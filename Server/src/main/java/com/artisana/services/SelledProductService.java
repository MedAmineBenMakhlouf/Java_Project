package com.artisana.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artisana.models.SelledProduct;
import com.artisana.models.User;
import com.artisana.repositories.SelledProductRepository;

@Service
public class SelledProductService {

	@Autowired
	private SelledProductRepository selledProductRepository;

	
	public SelledProduct createSelledProduct(SelledProduct p) {
		return selledProductRepository.save(p);
	}
	
	public List<SelledProduct> getAllByBuyer(User user)
	{
		return selledProductRepository.findByBuyer(user);
	}
	
	public List<SelledProduct> getAllBySeller(User user)
	{
		return selledProductRepository.findBySeller(user);
	}
	
}
