package com.artisana.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artisana.models.Participation;
import com.artisana.models.Product;
import com.artisana.models.ProductDTO;
import com.artisana.models.TraceParticipation;
import com.artisana.models.User;
import com.artisana.models.Wallet;
import com.artisana.services.ParticipationService;
import com.artisana.services.ProductService;
import com.artisana.services.TraceParticipationService;
import com.artisana.services.UserService;
import com.artisana.services.WalletService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/participation/")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ParticipationRestController {

	@Autowired
	private ParticipationService participationService;
	@Autowired
	private TraceParticipationService traceParticipationService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private WalletService walletService;

	@GetMapping("/{id}/participate")
	public ResponseEntity<?> ShowToParticipate(@PathVariable("id") Long productId, HttpSession session) {
		if ((Long) session.getAttribute("user_id") == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
		}
		try {
			User loggedUser= userService.findUser((Long) session.getAttribute("user_id"));
			Wallet wallet = walletService.findOne(loggedUser);
			Product product = productService.findOne(productId);
			Map<String, Object> response = new HashMap<>();
			if (participationService.lastParticipation(product.getId()) != null) {
				response.put("lastParticipation", participationService.lastParticipation(product.getId()).getAmount());
				response.put("lastParticipator", participationService.lastParticipation(product.getId()));

			} else {
				response.put("lastParticipation", 0);
			}
			Date currentDate = new Date();
			Boolean comparison = currentDate.after(product.getEndTime());
//			System.out.println(comparison);
			ProductDTO productDTO = new ProductDTO(product);
			response.put("nbrPart", traceParticipationService.getNumberOfParticipation(productId));
			response.put("comparison", comparison);
			response.put("participation", new Participation());
			response.put("product", productDTO);
			response.put("wallet", wallet);
			response.put("loggedUser", loggedUser);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving products: " + e.getMessage());
		}
	}

	@PostMapping("/{id}/addAuction")
	public ResponseEntity<?> bid(@Valid @RequestBody float amount, @PathVariable("id") Long id, HttpSession session,
			BindingResult result) {

		if (result.hasErrors()) {
			// Return validation errors
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}

		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			// User is not logged in
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User must be logged in to place a bid.");
		}

		Product product = productService.findOne(id);
		if (product == null) {
			// Product does not exist
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}

		User loggedUser = userService.findUser(userId);
		if (loggedUser == null) {
			// User does not exist
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		Participation participation = new Participation();
		participation.setAmount(amount);
		// Business logic validations
		Participation lastParticipation = participationService.lastParticipation(product.getId());
		if (lastParticipation != null && lastParticipation.getAmount() > participation.getAmount()) {
			// Bid is too low
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bid must be higher than the last bid.");
		}
		if (loggedUser.getWallet().getBalance() < participation.getAmount()) {
			// Insufficient funds
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance to place bid.");
		} else {
			product = productService.findOne(id);

			participation.setProduct(product);
			participation.setBuyer(loggedUser);
			 if (!participationService.getPreviousParticipations(id).isEmpty()) {
				for (Participation part : participationService.getPreviousParticipations(id)) {
					System.out.println("old amount " + part.getAmount());
					System.out.println(part.getBuyer().getWallet().getBalance());
					part.getBuyer().getWallet().setBalance(part.getBuyer().getWallet().getBalance() + part.getAmount());
					userService.updateUser(part.getBuyer());
					participationService.delete(part.getId());
				}
			} 
				
			participationService.save(participation);
			
			TraceParticipation traceParticipation = new TraceParticipation();
			traceParticipation.setProduct(product);
			traceParticipation.setBuyer(loggedUser);
			traceParticipation.setAmount(participation.getAmount());
			traceParticipationService.save(traceParticipation);

			loggedUser.getWallet().setBalance(loggedUser.getWallet().getBalance() - participation.getAmount());
			userService.updateUser(loggedUser);
		}
		return ResponseEntity.ok(participation);
	}
}
