package com.sms.uploadservice.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sms.uploadservice.exception.FileStorageException;
import com.sms.uploadservice.exception.MyFileNotFoundException;
import com.sms.uploadservice.properties.FileStorageProperties;


@Service
public class FileStorageService {
	
	private final Path fileStorageLocation;
	private final Path bulkFileStorageLocation;
	
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		
		this.fileStorageLocation = Paths.get(FileStorageProperties.getUploadDir())
				.toAbsolutePath().normalize();
		this.bulkFileStorageLocation = Paths.get(FileStorageProperties.getBulkUploadDir())
				.toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileStorageLocation);
			Files.createDirectories(this.bulkFileStorageLocation);
		}catch(Exception ex) {
			throw new  FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
		}
	}
	
public String storeFile(MultipartFile file)
{
	//Normalize file name
	String fileName= StringUtils.cleanPath(file.getOriginalFilename());
	
	try {
		//Check if the file's name contain invalid characters
		if(fileName.contains("..")) {
			throw new  FileStorageException("Sorry!! Filename contains invalid path sequence"+ fileName);
		}
		
		Path targetLocation =this.fileStorageLocation.resolve(fileName);
		Files.copy(file.getInputStream(),targetLocation,StandardCopyOption.REPLACE_EXISTING);
		
		return fileName;
	}catch(IOException ex) {
		throw new FileStorageException("Could not store file"+ fileName +".Please try again!");
	}
}

public String storeBulkUploadFile(MultipartFile file)
{
	//Normalize file name
	String fileName= StringUtils.cleanPath(file.getOriginalFilename());
	
	try {
		//Check if the file's name contain invalid characters
		if(fileName.contains("..")) {
			throw new  FileStorageException("Sorry!! Filename contains invalid path sequence"+ fileName);
		}
		
		Path targetLocation =this.bulkFileStorageLocation.resolve(fileName);
		Files.copy(file.getInputStream(),targetLocation,StandardCopyOption.REPLACE_EXISTING);
		
		return fileName;
	}catch(IOException ex) {
		throw new FileStorageException("Could not store file"+ fileName +".Please try again!");
	}
}

public Resource loadBulkUploadFileAsResource(String fileName) {
	
	try {
		Path filePath =this.bulkFileStorageLocation.resolve(fileName).normalize();
		Resource resource = new UrlResource(filePath.toUri());
		if(resource.exists()) {
			return resource;
		} else {
			throw new MyFileNotFoundException("File Not Found"+fileName);
		}
	}catch(MalformedURLException ex) {
		throw new MyFileNotFoundException("File Not Found"+fileName,ex);
	}
	// TODO Auto-generated method stub
	
}


public Resource loadFileAsResource(String fileName) {
	
	try {
		Path filePath =this.fileStorageLocation.resolve(fileName).normalize();
		Resource resource = new UrlResource(filePath.toUri());
		if(resource.exists()) {
			return resource;
		} else {
			throw new MyFileNotFoundException("File Not Found"+fileName);
		}
	}catch(MalformedURLException ex) {
		throw new MyFileNotFoundException("File Not Found"+fileName,ex);
	}
	// TODO Auto-generated method stub
	
}



}
