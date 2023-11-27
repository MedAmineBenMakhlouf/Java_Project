//package com.artisana.repositories;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//
//import com.artisana.models.Auction;
//import com.artisana.models.User;
//
//
//
//public interface AuctionRepository extends CrudRepository<Auction, Long> {
//	List<Auction> findAll();
//	
////	@Query(value="select a.* FROM auctions a JOIN users u ON a.buyer_id = u.id WHERE u.id=?1", nativeQuery = true)
//	List<Auction> findAllByBuyer(User user);
//	
//	@Query(value="select * from products where products.user_id=?1", nativeQuery = true)
//	List<Auction> findAllWithoutAuctions(Long user);
//}
