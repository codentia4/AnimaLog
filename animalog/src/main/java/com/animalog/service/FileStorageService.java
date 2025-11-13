package com.animalog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    
    @Value("${file.upload-dir:uploads/pet-images}")
    private String uploadDir;
    
    public String storeFile(MultipartFile file) {
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + extension;
            
            // Copy file to upload directory
            Path targetLocation = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            System.out.println("✅ File uploaded successfully: " + uniqueFilename);
            return "/uploads/pet-images/" + uniqueFilename;
            
        } catch (IOException e) {
            System.err.println("❌ Could not store file: " + e.getMessage());
            throw new RuntimeException("Could not store file. Please try again!", e);
        }
    }
    
    public void deleteFile(String filename) {
        try {
            if (filename != null && !filename.isEmpty()) {
                // Extract just the filename from the full path
                String justFilename = filename.substring(filename.lastIndexOf("/") + 1);
                Path filePath = Paths.get(uploadDir).resolve(justFilename).normalize();
                Files.deleteIfExists(filePath);
                System.out.println("✅ File deleted successfully: " + justFilename);
            }
        } catch (IOException e) {
            System.err.println("❌ Could not delete file: " + e.getMessage());
        }
    }
}