package com.artisana.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.artisana.models.Participation;
import com.artisana.models.Product;

public interface ParticipationRepository extends CrudRepository<Participation, Long> {
	List<Participation> findAll();
	Optional<Participation> findByProduct(Product p);
	List<Participation> findAllByProduct(Product p);
	
	@Query(value="select sum(amount) as sum from participations where product_id = :productId", nativeQuery = true)
	Double sumOfAuction(@Param("productId") Long id);
	
	@Query(value="select count(*) as sum from participations where product_id = :productId", nativeQuery = true)
	Double getNumberOfParticipation(@Param("productId") Long id);
	
	@Query(value="select * from participations where product_id = :productId order by id desc limit 1", nativeQuery = true)
	Participation lastParticipation(@Param("productId") Long id);
	
	@Query(value="select * from participations where product_id = :productId", nativeQuery = true)
	List<Participation> getPreviousParticipations(@Param("productId") Long id);
	
	@Query(value="insert into participations (product_id,user_id,amount) values (?1,?2,?3)", nativeQuery = true)
	void sajel(Long product,Long loggedUser,Double amount);
}
