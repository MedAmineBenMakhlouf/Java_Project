package com.artisana.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.artisana.models.Product;
import com.artisana.models.User;

public interface ProductRepository extends CrudRepository<Product, Long>{
	
	List<Product> findAll();
	
	@Query(value="select * FROM products WHERE type_product=1 and category like %:category%", nativeQuery = true)
	List<Product> findAllAuctions(@Param("category") String category);
	
	@Query(value="select * FROM products WHERE type_product=0 and category like %:category%", nativeQuery = true)
	List<Product> findAllWithoutAuctions(@Param("category") String category);
	
	List<Product> findByUser(User user);
	
	List<Product> findAllByCategory(String category);
	
	Optional<Product> findById(Long id);
}
