package com.artisana.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.artisana.models.User;
import com.artisana.models.Wallet;

public interface WalletRepository extends CrudRepository<Wallet, Long> {
	
	Optional<Wallet> findByUser(User user); 
	
}
