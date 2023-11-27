package com.artisana.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.artisana.models.File;
import com.artisana.models.Product;
import com.artisana.services.FileService;
import com.artisana.services.ProductService;

@RestController
@CrossOrigin(origins = "*")
public class FileRestController {

	@Autowired
	private FileService fileService;

	@Autowired
	private ProductService productService;

	public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

//	@DeleteMapping("/file/{productId}/{fileId}/delete")
//	public String deleteFile(@PathVariable("productId") Long productId, @PathVariable("fileId") Long fileId) {
//		fileService.deleteFile(fileId);
//		String path = "redirect:/product/" + productId + "/" + "edit";
//		return path;
//	}
	
    @DeleteMapping("/api/file/{productId}/{fileId}/delete")
    public ResponseEntity<?> deleteFile(@PathVariable("productId") Long productId, @PathVariable("fileId") Long fileId) {
        try {
            fileService.deleteFile(fileId);
            // Return a 200 OK response if the file deletion is successful
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // If there is an exception, it could be because the file doesn't exist or
            // because of some other issue like a database error. You should handle this
            // more gracefully depending on your application's requirements.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting file: " + e.getMessage());
        }
    }

//	@PostMapping("file/{productId}/add")
//	public String addFiles(@PathVariable("productId") Long productId, @RequestParam("file") MultipartFile[] files)
//			throws IOException {
//		Product prod = productService.findOne(productId);
//		String filename = "";
//		for (MultipartFile file : files) {
//			if (!file.isEmpty()) {
//				Path path = Paths.get(uploadDirectory);
//				filename = System.currentTimeMillis() + "_" + file.getOriginalFilename().toString();
//				Files.copy(file.getInputStream(), path.resolve(filename));
//			}
//		}
//		for (MultipartFile file : files) {
//			File newFile = new File();
//			Path path = Paths.get(uploadDirectory);
//			newFile.setPath(filename);
//			newFile.setProduct(prod);
//			fileService.createFile(newFile);
//		}
//		String path = "redirect:/product/"+productId+"/edit";
//		return path;
//	}
    
    @PostMapping("/api/file/{productId}/add")
    public ResponseEntity<?> addFiles(@PathVariable("productId") Long productId,
                                      @RequestParam("file") MultipartFile[] files) {
        try {
            Product prod = productService.findOne(productId);
            if (prod == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue; // Skip empty files.
                }

                String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDirectory);
                Files.copy(file.getInputStream(), path.resolve(filename));

                File newFile = new File();
                newFile.setPath(filename);
                newFile.setProduct(prod);
                fileService.createFile(newFile);
            }

            // Return a 201 Created response with the location of the uploaded files or some confirmation message
            return ResponseEntity.status(HttpStatus.CREATED).body("Files uploaded successfully");

        } catch (IOException e) {
            // Handle file I/O errors here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload files: " + e.getMessage());
        } catch (ResponseStatusException e) {
            // Handle other exceptions like product not found here
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}
