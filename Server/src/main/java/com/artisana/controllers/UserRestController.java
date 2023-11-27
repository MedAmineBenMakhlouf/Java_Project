package com.artisana.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.artisana.models.Login;
import com.artisana.models.Product;
import com.artisana.models.ProductDTO;
import com.artisana.models.Role;
import com.artisana.models.SelledProduct;
import com.artisana.models.User;
import com.artisana.models.UserDTO;
import com.artisana.models.Wallet;
import com.artisana.services.FileService;
import com.artisana.services.ProductService;
import com.artisana.services.RoleService;
import com.artisana.services.SelledProductService;
import com.artisana.services.UserService;
import com.artisana.services.WalletService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserRestController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private WalletService walletService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SelledProductService selledProductService;
	@Autowired
	private FileService fileService;

	public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

	
	@Value("${stripe.apikey}")
	String stripekey;
	
	@GetMapping("/loggedUser")
	public ResponseEntity<?> loggedUser(HttpSession session)
	{
		User user = userService.findUser((Long)session.getAttribute("user_id"));
//		Wallet wallet = walletService.findOne(user);
		Map<String, Object> response = new HashMap<>();

		response.put("user", user);
//		response.put("wallet", wallet);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestPart("user") @Valid User user,
            BindingResult result,
            @RequestPart("file") MultipartFile file,
            HttpSession session) throws IOException, StripeException {

		
		if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
		Path path = Paths.get(uploadDirectory);
		String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename().toString();
		Files.copy(file.getInputStream(), path.resolve(filename));
		user.setFile_path(filename);

		Role role = roleService.findByName("ROLE_USER");
		user.setRoles(role);
		
		
		Stripe.apiKey = stripekey;
		Map<String, Object> params = new HashMap<>();
		params.put("name", user.getUsername());
		params.put("email", user.getEmail());
//		user.setCustumerId(customer.getId());
		Customer customer = Customer.create(params);
		user.setCustumerId(customer.getId());
		User loggedUser = userService.register(user, result);
//		System.out.println(loggedUser.getId());
//		params.put("id", loggedUser.getId().toString());
		System.out.println(customer.getId());
		Wallet wallet = new Wallet();
		wallet.setBalance((double) 0);
		wallet.setUser(user);
		walletService.save(wallet);

		session.setAttribute("user_id", loggedUser.getId());
		return new ResponseEntity<>(loggedUser, HttpStatus.OK);

	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody Login logUser, BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			System.out.println("Login errors: " + result.getAllErrors());
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
		System.out.println(logUser);
		
		try {
			User loggedUser = userService.login(logUser, result);
			if (loggedUser == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password");
			}
			session.setAttribute("user_id", loggedUser.getId());
			
			return new ResponseEntity<>(loggedUser, HttpStatus.OK);

		} catch (Exception e) {
			// Log the exception for debugging purpose
			System.out.println("Login exception: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login error occurred");
		}
	}

	@GetMapping("/users")
	public ResponseEntity<?> allUsers() {
		try {
			List<User> users = userService.allUsers();
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace(); // Log the exception details
			return new ResponseEntity<>("Error fetching users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok("User logged out successfully!");
	}


	@GetMapping("/dashboardUser")
	public ResponseEntity<?> dashboardUser(HttpSession session) {
	    // ...
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not logged in.");
		}

	    List<Product> products = productService.getAll();
	    
	    List<ProductDTO> productDTOs = products.stream()
	                                           .map(ProductDTO::new)
	                                           .collect(Collectors.toList());


		User loggedUser = userService.findUser(userId);
		Wallet wallet = walletService.findOne(loggedUser);

		// Prepare the response as a Map, which will be converted to JSON
		Map<String, Object> response = new HashMap<>();
		response.put("wallet", wallet);
		response.put("loggedUser", loggedUser);
	    response.put("allProducts", productDTOs);

	    return ResponseEntity.ok(response);
	}


	@GetMapping("/user/{userId}/edit")
	public ResponseEntity<?> editUser(@PathVariable("userId") Long userId, HttpSession session) {
		Long sessionUserId = (Long) session.getAttribute("user_id");

		if (sessionUserId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
		}
		if (!userId.equals(sessionUserId)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authorized to edit this profile.");
		}

		User loggedUser = userService.findUser(sessionUserId);
		if (loggedUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}

		return ResponseEntity.ok(loggedUser);
	}



	@PutMapping("/user/{userId}/editPic")
	public ResponseEntity<?> editProfilePic(@PathVariable("userId") Long userId, HttpSession session,
			@RequestParam("file") MultipartFile file) throws IOException {
		Long sessionUserId = (Long) session.getAttribute("user_id");
		if (sessionUserId == null || !sessionUserId.equals(userId)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized or not logged in.");
		}

		if (file.isEmpty() || file.getOriginalFilename() == null) {
			return ResponseEntity.badRequest().body("File is required.");
		}

		User loggedUser = userService.findUser(sessionUserId);
		if (loggedUser != null) {
			Path path = Paths.get(uploadDirectory);
			String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			Files.copy(file.getInputStream(), path.resolve(filename));

			loggedUser.setFile_path(filename);
			userService.updateUser(loggedUser);

			// You might want to return the updated user or just a success message
			return ResponseEntity.ok().body("Profile picture updated successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
	}

//		@PutMapping("user/{userId}/edit")
//		public String editInfo(@PathVariable("userId") Long userId, @Valid @ModelAttribute("loggedUser") User user,
//				HttpSession session) {
//			User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
//			loggedUser.setUsername(user.getUsername());
//			loggedUser.setEmail(user.getEmail());
//			loggedUser.setPhone(user.getPhone());
//			loggedUser.setAddress(user.getAddress());
//			userService.updateUser(loggedUser);
//			return "redirect:/dashboardUser";
//		}

	@PutMapping("/user/{userId}/edit")
	public ResponseEntity<?> editInfo(@Valid @RequestBody User user,
			@PathVariable("userId") Long id,
			HttpSession session) {
		System.out.println(user.getUsername());
		System.out.println(user.getPhone());
		System.out.println(user.getAddress());
		Long sessionUserId = (Long) session.getAttribute("user_id");
	
		User loggedUser = userService.findUser(id);
		if (loggedUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}

		// Update user details
		loggedUser.setUsername(user.getUsername());
		loggedUser.setPhone(user.getPhone());
		loggedUser.setAddress(user.getAddress());

		userService.updateUser(loggedUser);
		return ResponseEntity.ok(loggedUser);
	}

	@GetMapping("/sellerDashboard")
	public ResponseEntity<?> sellerDashboard(HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not logged in.");
		}
		User loggedUser = userService.findUser(userId);
		List<SelledProduct> selledProducts = selledProductService.getAllByBuyer(loggedUser);
		List<Product> products = productService.findAllByUser(loggedUser);
		List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
		
		Map<String, Object> dashboardData = new HashMap<>();
		dashboardData.put("products", productDTOs);
		dashboardData.put("loggedUser", loggedUser);
		dashboardData.put("wallet", walletService.findOne(loggedUser));
		dashboardData.put("selledProducts", selledProducts);

		return ResponseEntity.ok(dashboardData);
	}
	

	@GetMapping("/user/{userId}/show")
    public ResponseEntity<?> detailSeller(@PathVariable("userId") Long id) {
        User seller = userService.findUser(id);
        List<Product> products = productService.findByUser(seller);
        UserDTO sellerDTO = new UserDTO();
        
        
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
        

        Map<String, Object> response = new HashMap<>();
        response.put("seller", seller);
        response.put("sellerProducts", productDTOs);

        return ResponseEntity.ok(response);
    }
}
