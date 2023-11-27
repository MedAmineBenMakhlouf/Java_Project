package com.artisana.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.artisana.models.File;
import com.artisana.models.Participation;
import com.artisana.models.Product;
import com.artisana.models.SelledProduct;
import com.artisana.models.User;
import com.artisana.services.FileService;
import com.artisana.services.ParticipationService;
import com.artisana.services.ProductService;
import com.artisana.services.SelledProductService;
import com.artisana.services.TraceParticipationService;
import com.artisana.services.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ProductController {

	
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
	private FileService fileService;
	
	@Autowired
	private SelledProductService selledProductService;


	public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

	@GetMapping("/product")
	public String getProduct(@ModelAttribute("product") Product product, HttpSession session, Model model) {
		if ((Long) session.getAttribute("user_id") == null) {
			return "redirect:/";
		}

		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
		model.addAttribute(loggedUser);
		return "addProduct.jsp";
	}

	
	@PostMapping("/product/add")
	public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model,
			HttpSession session, @RequestParam("file") MultipartFile[] files
	) throws IOException, ParseException {
		String filename = "";
		Product newProduct;
		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
		System.out.println(product.getEndTime());
		// If Auction is checked
		if (product.getTypeProduct() == true) {
			// Save the product
			product.setPrice((double) 1);

			product.setStartTime(new Date());

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, product.getDuration());
			product.setEndTime(calendar.getTime());
			

		}
		product.setStatus(false);
		product.setUser(loggedUser);
		newProduct = productService.createProduct(product);
		// Save picture(s) of product
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				Path path = Paths.get(uploadDirectory);
				filename = System.currentTimeMillis() + "_" + file.getOriginalFilename().toString();
				Files.copy(file.getInputStream(), path.resolve(filename));
			}
		}
		// Save the picture(s) product
		for (MultipartFile file : files) {
			File newFile = new File();
			Path path = Paths.get(uploadDirectory);
			newFile.setPath(filename);
			newFile.setProduct(newProduct);
			fileService.createFile(newFile);
		}
//		return "redirect:/product";
		return "redirect:/showMyProduct";
	}

	@PutMapping("/product/{productId}/update")
	public String updateProduct(@PathVariable("productId") Long productId,
			@Valid @ModelAttribute("product") Product product, BindingResult result) {

		Product thisProduct = productService.findOne(productId);
		thisProduct.setName(product.getName());
		thisProduct.setDescription(product.getDescription());
		thisProduct.setCategory(product.getCategory());
		productService.updateProduct(thisProduct);
		return "redirect:/showMyProduct";
	}

	@GetMapping("/showMyProduct")
	public String showAllProduct(Model model, HttpSession session) {
		if ((Long) session.getAttribute("user_id") == null) {
			return "redirect:/";
		}

		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
		model.addAttribute("productsByUser", productService.findByUser(loggedUser));
		model.addAttribute("loggedUser", loggedUser);
		return "showProducts.jsp";
	}

	@GetMapping("/product/{productId}/show")
	public String showProduct(@PathVariable("productId") Long id, HttpSession session, Model model) {
		if ((Long) session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
		Product product = productService.findOne(id);
		
		if (participationService.lastParticipation(product.getId()) != null) {
			model.addAttribute("lastParticipation", participationService.lastParticipation(product.getId()).getAmount());
			model.addAttribute("lastParticipator", participationService.lastParticipation(product.getId()));
		} else {
			model.addAttribute("lastParticipation", 0);
		}
		
		Date currentDate = new Date();
		System.out.println(currentDate);
//		Boolean comparison = currentDate.after(product.getEndTime());
//        model.addAttribute("comparison", comparison);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("product", product);
		return "showOne.jsp";
	}

	@GetMapping("/product/{id}/edit")
	public String editProduct(@PathVariable("id") Long id, HttpSession session, Model model) {
		if ((Long) session.getAttribute("user_id") == null) {
			return "redirect:/";
			
		}
		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
		Product product = productService.findOne(id);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("product", product);
		return "showForEdit.jsp";
	}
	
	@PostMapping("/sell/{id}")
	public String sell(@PathVariable("id") Long id,HttpSession session)
	{
		if(participationService.findById(id)==null)
		{
			return "showOne.jsp";
		}
		System.out.println(id);
		Participation participation = participationService.findById(id);
		User seller = userService.findUser((Long) session.getAttribute("user_id"));
		Product product = productService.findOne(participation.getProduct().getId());
		product.setStatus(true);
		productService.updateProduct(product);
		seller.getWallet().setBalance(seller.getWallet().getBalance()+participation.getAmount());
		userService.updateUser(seller);
		
		
		SelledProduct selledProduct = new SelledProduct();
		selledProduct.setBuyer(participation.getBuyer());
		selledProduct.setSeller(seller);
		selledProduct.setProduct(product.getName());
		
		selledProductService.createSelledProduct(selledProduct);
//		traceParticipationService.deleteAllByProduct(participation.getProduct().getId());
		

		return "redirect:/dashboardUser";
	}
	
//	@PostMapping("/product/{id}/buy")
//	public String buy(@PathVariable("id") Long id, HttpSession session)
//	{
//		Product product = productService.findOne(id);
//		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
//		
//		loggedUser.getWallet().setBalance(loggedUser.getWallet().getBalance()-product.getPrice());
//		
//		product.getUser().getWallet().setBalance(product.getUser().getWallet().getBalance()+product.getPrice());
//		
//		product.setStatus(true);
//		productService.updateProduct(product);
//		
//		
//		
//		
//		SelledProduct selledProduct = new SelledProduct();
//		selledProduct.setBuyer(loggedUser);
//		selledProduct.setSeller(product.getUser());
//		selledProduct.setProduct(product);
//		selledProductService.createSelledProduct(selledProduct);
//		
//		
//		return "https://example.com/success";
//	}
	
	@PostMapping("/product/{id}/buy")
	public String buy(@PathVariable("id") Long id, HttpSession session) throws StripeException {
	    Product product = productService.findOne(id);
	    User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
	    
	    loggedUser.getWallet().setBalance(loggedUser.getWallet().getBalance()-product.getPrice());
		
		product.getUser().getWallet().setBalance(product.getUser().getWallet().getBalance()+product.getPrice());
		
		product.setStatus(true);
		productService.updateProduct(product);

	    Stripe.apiKey = stripekey; // Ensure this is your Stripe secret key

	    // Create a Stripe Checkout session
	    SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
	        .setPriceData(
	            SessionCreateParams.LineItem.PriceData.builder()
	                .setCurrency("usd") // Set your currency
	                .setUnitAmount((long) (product.getPrice() * 100)) // Stripe uses the smallest currency unit
	                .setProductData(
	                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
	                        .setName(product.getName()) // Product name
	                        .build())
	                .build())
	        .setQuantity(1L)
	        .build();

	    SessionCreateParams params = SessionCreateParams.builder()
	        .addLineItem(lineItem)
	        .setMode(SessionCreateParams.Mode.PAYMENT)
	        .setSuccessUrl("http://localhost:8080/dashboardUser") // Set your success URL
	        .setCancelUrl("http://localhost:8080/dashboardUser") // Set your cancel URL
	        .build();

	    
	        Session Ssession = Session.create(params);

	        // Record the sale in your database
	        SelledProduct selledProduct = new SelledProduct();
	        selledProduct.setBuyer(loggedUser);
	        selledProduct.setSeller(product.getUser());
	        selledProduct.setProduct(product.getName());
	        selledProductService.createSelledProduct(selledProduct);

	        return "redirect:" + Ssession.getUrl(); // Redirect to Stripe's hosted checkout page
	    
	}
	
}
