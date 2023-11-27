package com.artisana.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.artisana.models.Login;
import com.artisana.models.User;
import com.artisana.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// TO-DO: Write register and login methods!
	public User register(User newUser, BindingResult result) {
		// TO-DO: Additional validations!
		Optional<User> optUser = userRepository.findByEmail(newUser.getEmail());
		if (optUser.isPresent()) {
			result.rejectValue("email", "registerError", "email is taken");
		}
		if (!newUser.getPassword().equals(newUser.getPasswordConfirmation())) {
			result.rejectValue("password", "registerError", "Password does't much");
		}
		if (result.hasErrors()) {
			System.out.println(result);
			return null;
			
		} else {
			String HashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
			newUser.setPassword(HashedPassword);
			User saveduser = userRepository.save(newUser);
			return saveduser;
		}
	}

	public User login(Login newLoginObject, BindingResult result) {
		// TO-DO: Additional validations!
		Optional<User> optUser = userRepository.findByEmail(newLoginObject.getEmail());
		if (!optUser.isPresent()) {
			result.rejectValue("email", "loginError", "email is not found");
		} else {
			User user = optUser.get();
			if (!BCrypt.checkpw(newLoginObject.getPassword(), user.getPassword())) {
				result.rejectValue("password", "loginError", "invalid Password");
			}
			if (result.hasErrors()) {
				return null;
			} else {
				return user;
			}
		}
		return null;
	}
	
	public List<User> allUsers()
	{
		return userRepository.findAll();
	}

	public User findUser(Long id) {

		Optional<User> opt = userRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			return null;
		}
	}
	
	public User updateUser(User user) {
		return userRepository.save(user);
	}
	
}
