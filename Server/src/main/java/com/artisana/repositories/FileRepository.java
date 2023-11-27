package com.artisana.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.artisana.models.File;
import com.artisana.models.Product;

public interface FileRepository extends CrudRepository<File, Long> {

	List<File> findAll();
	void deleteByProduct(Product p);
}
