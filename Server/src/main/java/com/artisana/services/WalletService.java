package com.artisana.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artisana.models.User;
import com.artisana.models.Wallet;
import com.artisana.repositories.WalletRepository;

@Service
public class WalletService {

	
	@Autowired
	private WalletRepository walletRepository;
	
	public Wallet save(Wallet wallet)
	{
		return walletRepository.save(wallet);
	}
	public Wallet update(Wallet wallet)
	{
		return walletRepository.save(wallet);
	}
	
	
	public Wallet findOne(User user) {
		Optional<Wallet> OptWallet = walletRepository.findByUser(user);
		if (OptWallet.isPresent()) {
			return OptWallet.get();

		} else
			return null;
	}
}
