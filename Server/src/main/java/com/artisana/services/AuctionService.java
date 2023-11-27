//package com.artisana.services;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.artisana.models.Auction;
//import com.artisana.models.User;
//import com.artisana.repositories.AuctionRepository;
//
//@Service
//public class AuctionService {
//	@Autowired
//	AuctionRepository auctionRepository;
//	public List<Auction> getAll()
//	{
//		return auctionRepository.findAll();
//	}
//	
//	public Auction createAuction(Auction a)
//	{
//		return auctionRepository.save(a);
//	}
//	
//	public List<Auction> findAllAuctions(User user)
//	{
//		System.out.println(user);
//		return auctionRepository.findAllByBuyer(user);
//	}
//	
//	public List<Auction> findAllWithoutAuctions(Long user)
//	{
//		System.out.println(user);
//		return auctionRepository.findAllWithoutAuctions(user);
//	}
//	
//	public Auction findOne(Long id)
//	{
//		Optional<Auction> OptProduct= auctionRepository.findById(id);
//		if(OptProduct.isPresent())
//		{
//		return OptProduct.get();
//		}
//		else return null;
//	}
//	
//	public Auction updateAuction(Auction a)
//	{
//		
//		return auctionRepository.save(a);
//	}
//	
//	public void deleteAuction(Long id)
//	{
//		auctionRepository.deleteById(id);
//	}
//	
//}
