package com.artisana.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.artisana.models.File;
import com.artisana.models.Participation;
import com.artisana.models.Product;
import com.artisana.models.ProductDTO;
import com.artisana.models.SelledProduct;
import com.artisana.models.User;
import com.artisana.services.FileService;
import com.artisana.services.ParticipationService;
import com.artisana.services.ProductService;
import com.artisana.services.SelledProductService;
import com.artisana.services.TraceParticipationService;
import com.artisana.services.UserService;
import com.artisana.services.WalletService;
import com.stripe.exception.StripeException;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ProductRestController {

	@Value("${stripe.apikey}")
	String stripekey;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ParticipationService participationService;

	@Autowired
	private TraceParticipationService traceParticipationService;

	@Autowired
	private SelledProductService selledProductService;
	@Autowired
	private WalletService walletService;

	@Autowired
	private FileService fileService;


	public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

	// Method to show product to
	@GetMapping("/{productId}/findOne")
	public ResponseEntity<?> getOneProduct(@SessionAttribute(name = "user_id", required = false) Long userId,
			@PathVariable("productId") Long id) {
		if (userId == null) {
			// User is not logged in, return an unauthorized error
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User must be logged in.");
		}

		User loggedUser = userService.findUser(userId);
		if (loggedUser == null) {
			// User does not exist, return a not found error
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}

		Product product = productService.findOne(id);
		ProductDTO productDTO = new ProductDTO(product);

		if (product == null) {
			// User does not exist, return a not found error
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}

		return ResponseEntity.ok(productDTO);
	}

	@GetMapping("/{category}")
	public ResponseEntity<?> getProduct(@PathVariable("category") String category, HttpSession session) {

		Long userId = (Long) session.getAttribute("user_id");

		if (userId == null) {
			// User is not logged in, return an unauthorized error
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User must be logged in.");
		}

		User loggedUser = userService.findUser(userId);
		if (loggedUser == null) {
			// User does not exist, return a not found error
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		Map<String, Object> response = new HashMap<>();

		List<Product> auctionProducts = productService.findAllAuctions(category);
		List<Product> withoutAuctionProducts = productService.findAllWithoutAuctions(category);
		
		List<ProductDTO> auctionsProductDTOs = auctionProducts.stream().map(ProductDTO::new).collect(Collectors.toList());
		List<ProductDTO> withoutAuctionsProductDTOs = withoutAuctionProducts.stream().map(ProductDTO::new).collect(Collectors.toList());
		
		response.put("category", category);
		response.put("loggedUser", loggedUser);
		response.put("auctionProducts", auctionsProductDTOs);
		response.put("withoutAuctionProducts", withoutAuctionsProductDTOs);
		response.put("wallet", walletService.findOne(loggedUser));
		return ResponseEntity.ok(response);
	}

	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@Valid @RequestPart("product") Product product, BindingResult result,
			@RequestPart("files") MultipartFile[] files, Principal principal, HttpSession session) throws IOException {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}

		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));

		if (loggedUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		product.setStatus(false);
		product.setUser(loggedUser);

		// If Auction is checked
		if (product.getTypeProduct()) {
			product.setPrice(1.0); // Set auction starting price
			product.setStartTime(new Date()); // Auction start time

			// Calculate end time based on duration
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, product.getDuration());
			product.setEndTime(calendar.getTime());
		}

		// Create the product
		Product newProduct = productService.createProduct(product);

		// Process and save files
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path path = Paths.get(uploadDirectory);
				Files.copy(file.getInputStream(), path.resolve(filename));

				// Create and save file metadata
				File newFile = new File();
				newFile.setPath(filename);
				newFile.setProduct(newProduct);
				fileService.createFile(newFile);
			}
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
	}

	@GetMapping("/showAllMine")
	public ResponseEntity<?> showAllMyProduct(HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		System.out.println(userId);
		if (userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
		}

		User loggedUser = userService.findUser(userId);
		if (loggedUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		try {
			Map<String, Object> response = new HashMap<>();
			response.put("productsByUser", productService.findByUser(loggedUser));
			response.put("loggedUser", loggedUser);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving products: " + e.getMessage());
		}
	}

	@PutMapping("/{productId}/update")
	public ResponseEntity<Product> updateProduct(@PathVariable("productId") Long id,
			@Valid @RequestBody Product product) {
		Product updatedProduct = productService.updateProduct(product);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

//	@GetMapping("/{productId}/show")
//	public ResponseEntity<?> showProduct(@PathVariable("productId") Long id, Principal principal, HttpSession session) {
//		Long userId = (Long) session.getAttribute("user_id");
//		if (userId == null) {
//			// User is not logged in or session has expired
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to view the product.");
//		}
//		User loggedUser = userService.findUser(userId);// Replace findByUsername with your actual method
//		Map<String, Object> response = new HashMap<>();
//		// Check if the user is not found
//		if (loggedUser == null) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//
//		// Find the product
//		Product product = productService.findOne(id);
//		if (product == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		}

	@GetMapping("/{productId}/show")
	public ResponseEntity<?> showProduct(@PathVariable("productId") Long id, Principal principal, HttpSession session) {
		System.out.println(id);
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			// User is not logged in or session has expired
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to view the product.");
		}
		User loggedUser = userService.findUser(userId);
		Map<String, Object> response = new HashMap<>();
		// Check if the user is not found
		if (loggedUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		// Find the product
		Product product = productService.findOne(id);
		ProductDTO productDTO = new ProductDTO(product);
		System.out.println(product);
		if (product == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		if (participationService.lastParticipation(product.getId()) != null) {
			response.put("lastParticipation", participationService.lastParticipation(product.getId()).getAmount());
			response.put("lastParticipator", participationService.lastParticipation(product.getId()));
		} else {
			response.put("lastParticipation", 0);
		}
		Boolean comparison = false;
		Date currentDate = new Date();
		if (product.getTypeProduct() == true) {
			comparison = currentDate.after(product.getEndTime());
		}
		response.put("comparison", comparison);
//		response.put("loggedUser", loggedUser);
		response.put("product", productDTO);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/all")
	public ResponseEntity<?> allProducts() {
		return ResponseEntity.ok().body(productService.getAll());
	}

	@GetMapping("/{id}/edit")
	public ResponseEntity<?> editProduct(@PathVariable("id") Long id, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		User loggedUser = userService.findUser(userId);
		if (loggedUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Product product = productService.findOne(id);
		if (product == null) {
			// Product does not exist, return a not found error
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// Return the product entity with a 200 OK status
		return ResponseEntity.ok(product);
	}

	@PostMapping("/sell/{productId}")
	public ResponseEntity<?> sell(@PathVariable("productId") Long productId,
			HttpSession session) throws StripeException {
		
		Product product = productService.findOne(productId);
		Participation participation = participationService.findByProduct(product);
	

		User buyer = participation.getBuyer();
		User seller = product.getUser();


		Map<String, String> response = new HashMap<>();
		product.setStatus(true);
		productService.updateProduct(product);

		seller.getWallet().setBalance(seller.getWallet().getBalance() + participation.getAmount());
		userService.updateUser(seller);

		SelledProduct selledProduct = new SelledProduct();
		selledProduct.setBuyer(participation.getBuyer());
		selledProduct.setSeller(seller);
		selledProduct.setProduct(product.getName());
		selledProductService.createSelledProduct(selledProduct);
//		emailService.sendSimpleMessage(buyer.getEmail(), "Transaction Details", "Transaction successful for product XYZ.");
//		emailService.sendSimpleMessage(seller.getEmail(), "Transaction Details", "Your product XYZ has been sold.");
//		traceParticipationService.deleteAllByProduct(participation.getProduct().getId());
		participationService.delete(participation.getId());

		return ResponseEntity.ok(response);
	}
	
	@Transactional
	@DeleteMapping("/{productId}/remove")
	public ResponseEntity<?> delete(@PathVariable("productId") Long productId, HttpSession session) {
	    try {
	        Product product = productService.findOne(productId);
	        if (product == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	        }

	        fileService.deleteByProduct(product);
	        productService.deleteProduct(productId);

	        return ResponseEntity.ok(product);
	    } catch (Exception e) {
	        // Log the exception details for debugging
	        // Return an internal server error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting the product");
	    }
	}

	@PostMapping("/{productId}/{userId}/buy")
	public ResponseEntity<?> buy(@PathVariable("productId") Long productId, @PathVariable("userId") Long userId,
			HttpSession httpsession) throws StripeException {

		Product product = productService.findOne(productId);
		if (product == null) {
			// Use status() method to return a 404 Not Found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		User loggedUser = userService.findUser(userId);

		if (loggedUser == null) {
			// Use status() to return a 403 Forbidden
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found or not logged in.");
		}


		loggedUser.getWallet().setBalance(loggedUser.getWallet().getBalance() - product.getPrice());

		product.getUser().getWallet().setBalance(product.getUser().getWallet().getBalance() + product.getPrice());

		product.setStatus(true);
		productService.updateProduct(product);
		System.out.println(loggedUser.getEmail());
		System.out.println(product.getUser().getEmail());
//		emailService.sendSimpleMessage(loggedUser.getEmail(), "Transaction Details", "Transaction successful for product XYZ.");
//		emailService.sendSimpleMessage(product.getUser().getEmail(), "Transaction Details", "Your product XYZ has been sold.");

		SelledProduct selledProduct = new SelledProduct();
		selledProduct.setBuyer(loggedUser);
		selledProduct.setSeller(product.getUser());
		selledProduct.setProduct(product.getName());
		selledProduct.setPrice(product.getPrice());
		selledProductService.createSelledProduct(selledProduct);
		return ResponseEntity.ok(selledProduct);
	}
	
	@GetMapping("/selled")
	public ResponseEntity<?> selledProduct(HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not logged in.");
		}
		User loggedUser = userService.findUser(userId);
		List<SelledProduct> boughtProducts = selledProductService.getAllBySeller(loggedUser);
		
		
		Map<String, Object> data = new HashMap<>();
		data.put("loggedUser", loggedUser);
		data.put("selledProducts", boughtProducts);

		return ResponseEntity.ok(data);
	}
	@GetMapping("/bought")
	public ResponseEntity<?> boughtProduct(HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not logged in.");
		}
		User loggedUser = userService.findUser(userId);
		List<SelledProduct> selledProducts = selledProductService.getAllByBuyer(loggedUser);
		
		
		Map<String, Object> data = new HashMap<>();
		data.put("loggedUser", loggedUser);
		data.put("boughtProducts", selledProducts);
		return ResponseEntity.ok(data);
	}
	

}
