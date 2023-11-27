package com.artisana.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.artisana.models.User;
import com.artisana.models.Wallet;
import com.artisana.services.UserService;
import com.artisana.services.WalletService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class WalletController {

	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private WalletService walletService; 
	
	@GetMapping("/configWallet")
	public String getWallet(HttpSession session, Model model)
	{
		
		if ((Long) session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
		Wallet wallet = walletService.findOne(loggedUser);
		model.addAttribute("wallet", wallet);
		return "wallet.jsp";
	}
	
	@PutMapping("/wallet/update/{id}")
	public String saveWallet(@Valid @ModelAttribute("wallet") Wallet wallet,@PathVariable("id") Long walletId,HttpSession session)
	{
		User user = userService.findUser((Long) session.getAttribute("user_id"));
		wallet.setUser(user);
		Wallet oldWallet = walletService.findOne(user);
		wallet.setBalance(wallet.getBalance()+oldWallet.getBalance());
		walletService.update(wallet);
		return "redirect:/sellerDashboard";
	}
}
