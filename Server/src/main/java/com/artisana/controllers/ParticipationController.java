package com.artisana.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.artisana.models.Participation;
import com.artisana.models.Product;
import com.artisana.models.TraceParticipation;
import com.artisana.models.User;
import com.artisana.services.ParticipationService;
import com.artisana.services.ProductService;
import com.artisana.services.TraceParticipationService;
import com.artisana.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ParticipationController {

	@Autowired
	private ParticipationService participationService;
	@Autowired
	private TraceParticipationService traceParticipationService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;

	@GetMapping("/product/{id}/participate")
	public String ShowToParticipate(@PathVariable("id") Long productId, HttpSession session, Model model) {
		if ((Long) session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		Product product = productService.findOne(productId);
		if (participationService.lastParticipation(product.getId()) != null) {
			model.addAttribute("lastParticipation",
					participationService.lastParticipation(product.getId()).getAmount());

			model.addAttribute("lastParticipator", participationService.lastParticipation(product.getId()));

		} else {
			model.addAttribute("lastParticipation", 0);
		}

		// TODO OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
		Date currentDate = new Date();
		Boolean comparison = currentDate.after(product.getEndTime());
		System.out.println(comparison);
		model.addAttribute("comparison", comparison);
		// OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
		model.addAttribute("participation", new Participation());
		model.addAttribute("product", product);
		model.addAttribute("loggedUser", userService.findUser((Long) session.getAttribute("user_id")));
		return "participate.jsp";
	}

	@PostMapping("/product/{id}/addAuction")
	public String biding(@Valid @ModelAttribute("participation") Participation participation,
			@PathVariable("id") Long id, HttpSession session, BindingResult result) {
		Product product = productService.findOne(id);
		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
		if (participationService.lastParticipation(product.getId()) != null) {
			if (participationService.lastParticipation(product.getId()).getAmount() > participation.getAmount()) {
				String path = "redirect:/product/" + id + "/participate";
				return path;
			}
		}
		if (loggedUser.getWallet().getBalance() - participation.getAmount() < 0) {
			// ERROR
			return "participate.jsp";
		} else {

			product = productService.findOne(id);

			participation.setProduct(product);
			participation.setBuyer(loggedUser);
			participation.setAmount(participation.getAmount());
			participationService.save(participation);
			if (!participationService.getPreviousParticipations(id).isEmpty()) {

				for (Participation part : participationService.getPreviousParticipations(id)) {
					if (part != null) {
						part.getBuyer().getWallet()
								.setBalance(part.getBuyer().getWallet().getBalance() + part.getAmount());
						userService.updateUser(part.getBuyer());
						participationService.delete(part.getId());
					}
				}

			}

			TraceParticipation traceParticipation = new TraceParticipation();
			traceParticipation.setProduct(product);
			traceParticipation.setBuyer(loggedUser);
			traceParticipation.setAmount(participation.getAmount());
			traceParticipationService.save(traceParticipation);
//			participationService.tasjil(product.getId(),loggedUser.getId(),participation.getAmount());

			loggedUser.getWallet().setBalance(loggedUser.getWallet().getBalance() - participation.getAmount());
			userService.updateUser(loggedUser);

		}
//		Participation participationInProduct = participationService.findOne(product);

		String path = "redirect:/product/" + id + "/participate";
		return path;
	}
}
