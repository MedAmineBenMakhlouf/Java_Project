package com.artisana.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.artisana.models.SelledProduct;
import com.artisana.models.User;

public interface SelledProductRepository extends CrudRepository<SelledProduct, Long> {

	List<SelledProduct> findByBuyer(User buyer);
	List<SelledProduct> findBySeller(User seller);
}
