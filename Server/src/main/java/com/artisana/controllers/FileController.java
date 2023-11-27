package com.artisana.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.artisana.models.File;
import com.artisana.models.Product;
import com.artisana.services.FileService;
import com.artisana.services.ProductService;

@Controller
public class FileController {

	@Autowired
	private FileService fileService;

	@Autowired
	private ProductService productService;

	public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

	@DeleteMapping("/file/{productId}/{fileId}/delete")
	public String deleteFile(@PathVariable("productId") Long productId, @PathVariable("fileId") Long fileId) {
//		File file = fileService.findOne(fileId);
		fileService.deleteFile(fileId);
		String path = "redirect:/product/" + productId + "/" + "edit";
		return path;
	}

	@PostMapping("file/{productId}/add")
	public String addFiles(@PathVariable("productId") Long productId, @RequestParam("file") MultipartFile[] files)
			throws IOException {
		Product prod = productService.findOne(productId);
		String filename = "";
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				Path path = Paths.get(uploadDirectory);
				filename = System.currentTimeMillis() + "_" + file.getOriginalFilename().toString();
				Files.copy(file.getInputStream(), path.resolve(filename));
			}
		}
		for (MultipartFile file : files) {
			File newFile = new File();
			Path path = Paths.get(uploadDirectory);
			newFile.setPath(filename);
			newFile.setProduct(prod);
			fileService.createFile(newFile);
		}
		String path = "redirect:/product/"+productId+"/edit";
		return path;
	}
}
