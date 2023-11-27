package com.artisana.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.artisana.models.Product;
import com.artisana.models.TraceParticipation;

import jakarta.transaction.Transactional;

public interface TraceParticipationRepository extends CrudRepository<TraceParticipation, Long> {
	List<TraceParticipation> findAll();
	Optional<TraceParticipation> findByProduct(Product p);
	
	@Query(value="select sum(amount) as sum from traces_participations where product_id = :productId", nativeQuery = true)
	Double sumOfAuction(@Param("productId") Long id);
	
	@Query(value="select count(*) as sum from traces_participations where product_id = :productId", nativeQuery = true)
	Double getNumberOfParticipation(@Param("productId") Long id);
	
	@Query(value="select * from traces_participations where product_id = :productId order by id desc limit 1", nativeQuery = true)
	TraceParticipation lastParticipation(@Param("productId") Long id);
	
	@Query(value="select * from traces_participations where product_id = :productId and id < (SELECT MAX(id) FROM traces_participations)", nativeQuery = true)
	List<TraceParticipation> getPreviousParticipations(@Param("productId") Long id);
	
	@Query(value="insert into traces_participations (product_id,user_id,amount) values (?1,?2,?3)", nativeQuery = true)
	void sajel(Long product,Long loggedUser,Double amount);
	
	void deleteByProduct(Product p);
	
	@Modifying
    @Transactional
	@Query(value="delete from traces_participations where product_id= :productId", nativeQuery = true)
	void deleteAllTheProducts(@Param("productId") Long id);
}
