package com.artisana.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artisana.models.User;
import com.artisana.models.Wallet;
import com.artisana.services.UserService;
import com.artisana.services.WalletService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class WalletRestController {

	@Value("${stripe.apikey}")
	String stripekey;

	@Autowired
	private UserService userService;

	@Autowired
	private WalletService walletService;

//	@GetMapping("/configWallet")
//	public String getWallet(HttpSession session, Model model)
//	{
//		
//		if ((Long) session.getAttribute("user_id") == null) {
//			return "redirect:/";
//		}
//		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
//		Wallet wallet = walletService.findOne(loggedUser);
//		model.addAttribute("wallet", wallet);
//		return "wallet.jsp";
//	}

	@GetMapping("/configWallet")
	public ResponseEntity<?> getWallet(HttpSession session) {
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

		Wallet wallet = walletService.findOne(loggedUser);
		if (wallet == null) {
			// Wallet does not exist for the user, you can decide to create one, or return a
			// not found error
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found.");
		}

		// Return the wallet data with a 200 OK status
		return ResponseEntity.ok(wallet);
	}

//	@PutMapping("/wallet/update/{id}")
//	public String saveWallet(@Valid @ModelAttribute("wallet") Wallet wallet,@PathVariable("id") Long walletId,HttpSession session)
//	{
//		User user = userService.findUser((Long) session.getAttribute("user_id"));
//		wallet.setUser(user);
//		Wallet oldWallet = walletService.findOne(user);
//		wallet.setBalance(wallet.getBalance()+oldWallet.getBalance());
//		walletService.update(wallet);
//		return "redirect:/sellerDashboard";
//	}

	@PutMapping("/wallet/update")
	public ResponseEntity<?> saveWallet(@Valid @RequestBody Float amount, HttpSession session)
			throws StripeException {

		Stripe.apiKey = stripekey;

		if (amount < 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("provide a positive amount");
		}
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			// User is not logged in, return an unauthorized error
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		User user = userService.findUser(userId);
		if (user == null) {
			// User does not exist, return a not found error
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		Wallet oldWallet = walletService.findOne(user);
		SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
			    .setPriceData(
			        SessionCreateParams.LineItem.PriceData.builder()
			            .setCurrency("usd") // Set your currency
			            .setUnitAmount((long) (amount * 100)) // Amount in the smallest unit (e.g., cents for USD)
			            .setProductData(
			                SessionCreateParams.LineItem.PriceData.ProductData.builder()
			                    .setName(user.getUsername()) // You can give a generic name
			                    .build()
			            )
			            .build()
			    )
			    .setQuantity(1L)
			    .build();
		SessionCreateParams params = SessionCreateParams.builder().addLineItem(lineItem)
				.setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl("http://localhost:4200/dashboard")																	
				.setCancelUrl("https://example.com/cancel")
				.build();

		Session Ssession = Session.create(params);
		System.out.println(oldWallet.getBalance());
		System.out.println(amount);
		
		// Update the wallet balance by adding the new balance to the existing one
		oldWallet.setBalance(amount + oldWallet.getBalance());
		Map<String, String> response = new HashMap<>();
		response.put("sessionId", Ssession.getId());
		// Persist the changes to the wallet
		walletService.update(oldWallet);

		// Return the updated wallet data with a 200 OK status
		return ResponseEntity.ok(response);
	}
	
	
	@PutMapping("/wallet/withdraw")
	public ResponseEntity<?> withdraw(@Valid @RequestBody Float amount, HttpSession session)
			throws StripeException {

		Stripe.apiKey = stripekey;

		if (amount < 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("provide a positive amount");
		}
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			// User is not logged in, return an unauthorized error
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		User user = userService.findUser(userId);
		if (user == null) {
			// User does not exist, return a not found error
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		Wallet oldWallet = walletService.findOne(user);
		if((oldWallet.getBalance()-amount)<0)
		 {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("you cannot withdraw more than your balance ");
		}
		SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
			    .setPriceData(
			        SessionCreateParams.LineItem.PriceData.builder()
			            .setCurrency("usd") // Set your currency
			            .setUnitAmount((long) (amount * 100)) // Amount in the smallest unit (e.g., cents for USD)
			            .setProductData(
			                SessionCreateParams.LineItem.PriceData.ProductData.builder()
			                    .setName(user.getUsername()) // You can give a generic name
			                    .build()
			            )
			            .build()
			    )
			    .setQuantity(1L)
			    .build();
		SessionCreateParams params = SessionCreateParams.builder().addLineItem(lineItem)
				.setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl("http://localhost:4200/dashboard")																	
				.setCancelUrl("https://example.com/cancel")
				.build();

		Session Ssession = Session.create(params);
		// Update the wallet balance by adding the new balance to the existing one
		oldWallet.setBalance(oldWallet.getBalance()-amount);
		Map<String, String> response = new HashMap<>();
		response.put("sessionId", Ssession.getId());
		// Persist the changes to the wallet
		walletService.update(oldWallet);

		// Return the updated wallet data with a 200 OK status
		return ResponseEntity.ok(response);
	}
	
}
