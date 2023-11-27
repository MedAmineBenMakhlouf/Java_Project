package com.artisana.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.artisana.models.Login;
import com.artisana.models.Product;
import com.artisana.models.Role;
import com.artisana.models.SelledProduct;
import com.artisana.models.User;
import com.artisana.models.Wallet;
import com.artisana.services.ParticipationService;
import com.artisana.services.ProductService;
import com.artisana.services.RoleService;
import com.artisana.services.SelledProductService;
import com.artisana.services.UserService;
import com.artisana.services.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private WalletService walletService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ParticipationService participationService;

	@Autowired
	private SelledProductService selledProductService;

	public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

	private final ObjectMapper objectMapper;

	@Autowired
	public UserController(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@GetMapping("/")
	public String LogReg(Model model) {

		model.addAttribute("register", new User());
		model.addAttribute("login", new Login());
		return "index.jsp";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("register") User user, BindingResult result, Model model,
			HttpSession session, @RequestParam(value = "file", required = false) MultipartFile file)
			throws IOException {

		if (!file.isEmpty()) {
			Path path = Paths.get(uploadDirectory);
			String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename().toString();
			Files.copy(file.getInputStream(), path.resolve(filename));
			user.setFile_path(filename);
		}
		Role role = roleService.findByName("ROLE_USER");
		user.setRoles(role);
		User loggedUser = userService.register(user, result);

		Wallet wallet = new Wallet();
		wallet.setBalance((double) 0);
		wallet.setUser(user);
		walletService.save(wallet);

		if (result.hasErrors()) {
			model.addAttribute("login", new Login());
			return "index.jsp";
		} else {
			session.setAttribute("user_id", loggedUser.getId());
			return "redirect:/dashboardUser";
		}
	}

	@GetMapping("/dashboardUser")
	public String dashboard(HttpSession session, Model model) {
		if ((Long) session.getAttribute("user_id") == null) {
			return "redirect:/";
		}

		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
		model.addAttribute("wallet", walletService.findOne(loggedUser));
		model.addAttribute("loggedUser", loggedUser);
		List<Product> products = productService.getAll();
		model.addAttribute("allProducts", products);

		return "dashboardUser.jsp";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("login") Login user, BindingResult result, Model model,
			HttpSession session) {
		User loogedUser = userService.login(user, result);
		if (result.hasErrors()) {
			model.addAttribute("register", new User());
			return "index.jsp";
		} else {
			session.setAttribute("user_id", loogedUser.getId());
			return "redirect:/dashboardUser";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/user/{userId}/edit")
	public String showDetails(@PathVariable("userId") Long userId, HttpSession session, Model model) {
		if ((Long) session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
		model.addAttribute("loggedUser", loggedUser);
		return "showUser.jsp";
	}

	@PutMapping("/user/{userId}/editPic")
	public String editProfilePic(@PathVariable("userId") Long userId, HttpSession session,
			@RequestParam("file") MultipartFile file) throws IOException {
		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
		if (file.getOriginalFilename() != null) {
			Path path = Paths.get(uploadDirectory);
			String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename().toString();
			Files.copy(file.getInputStream(), path.resolve(filename));
			loggedUser.setFile_path(filename);
			userService.updateUser(loggedUser);
		} else {
			// TODO return file required
		}
		String page = "redirect:/user/" + userId + "/edit";
		return page;
	}

	@PutMapping("user/{userId}/edit")
	public String editInfo(@PathVariable("userId") Long userId, @Valid @ModelAttribute("loggedUser") User user,
			HttpSession session) {
		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));
		loggedUser.setUsername(user.getUsername());
		loggedUser.setEmail(user.getEmail());
		loggedUser.setPhone(user.getPhone());
		loggedUser.setAddress(user.getAddress());
		userService.updateUser(loggedUser);
//		String page = "redirect:/user/"+userId+"/edit";
		return "redirect:/dashboardUser";
	}

	@GetMapping("/sellerDashboard")
	public String sellerDashboard(HttpSession session, Model model) {
		if ((Long) session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		User loggedUser = userService.findUser((Long) session.getAttribute("user_id"));

//		List<SelledProduct> selledProducts = selledProductService.getAllBuyers(loggedUser);

		// Assuming Product has 'name' and 'price' fields that you want to chart

//		List<String> names = selledProducts.stream().map(selledProduct -> selledProduct.getProduct().getName())
//				.collect(Collectors.toList());
//		List<Double> prices = selledProducts.stream().map(selledProduct -> selledProduct.getProduct().getPrice())
//				.collect(Collectors.toList());

//		try {
//			String jsonNames = objectMapper.writeValueAsString(names);
//			String jsonPrices = objectMapper.writeValueAsString(prices);
//
//			model.addAttribute("jsonNames", jsonNames);
//			model.addAttribute("jsonPrices", jsonPrices);
//		} catch (Exception e) {
//			// Handle the exception
//			e.printStackTrace();
//		}
		model.addAttribute("wallet", walletService.findOne(loggedUser));
		return "sellerDashboard.jsp";
	}

	@GetMapping("/user/{userId}/show")
	public String detailSeller(@PathVariable("userId") Long id, Model model) {
		User seller = userService.findUser(id);
		Double nbreOfParticipation = participationService.getNumberOfParticipation(seller.getId());
		model.addAttribute("seller", seller);
		return "detailsSeller.jsp";
	}
}
