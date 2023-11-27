package com.artisana.services;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.artisana.models.Product;
import com.artisana.models.User;
import com.artisana.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> getAll() {
		return productRepository.findAll();
	}

	public Product createProduct(Product p) {
		return productRepository.save(p);
	}

	public List<Product> findAllByUser(User user)
	{
		return productRepository.findByUser(user);
	}
	public Product findOne(Long id) {
		Optional<Product> OptProduct = productRepository.findById(id);
		if (OptProduct.isPresent()) {
			return OptProduct.get();

		} else
			return null;
	}

	public List<Product> getAllByCategory(String category)
	{
		return productRepository.findAllByCategory(category);
	}
	public List<Product> findAllAuctions(String category) {
		return productRepository.findAllAuctions(category);
	}
	

	public List<Product> findAllWithoutAuctions(String category) {
		return productRepository.findAllWithoutAuctions(category);
	}

	public List<Product> findByUser(User user) {
		return productRepository.findByUser(user);
	}
	public Product updateProduct(Product p) {
		return productRepository.save(p);
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	public Resource loadFileAsResource(String fileName) throws MalformedURLException {
		Path fileStorageLocation = Paths.get(System.getProperty("user.dir") + "/uploads").toAbsolutePath().normalize();
		Path filePath = fileStorageLocation.resolve(fileName).normalize();
		Resource resource = new UrlResource(filePath.toUri());
		if (resource.exists()) {
			return resource;
		}
		return null;
	}
}
