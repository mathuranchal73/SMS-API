package com.sms.uploadservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sms.uploadservice.payload.UploadFileResponse;
import com.sms.uploadservice.service.FileStorageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value="upload", description = "Data service operations on Upload Service", tags=("upload-service"))
public class UploadController {
	
	private static final Logger logger= LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@PostMapping("/uploadFile")
	@ApiOperation(value="Upload the file", notes="Uploads a Multipart File and returns the download URI",produces = "application/json", nickname="uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile File) {
		String fileName=fileStorageService.storeFile(File);
		
		String fileDownloadUri=ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(fileName)
				.toUriString();
		
		return new UploadFileResponse(fileName,fileDownloadUri,File.getContentType(),File.getSize());
	}
	
	@PostMapping("/uploadBulkFile")
	@ApiOperation(value="Upload the file", notes="Uploads a Multipart File and returns the download URI",produces = "application/json", nickname="uploadFile")
	public UploadFileResponse uploadBulkFile(@RequestParam("file") MultipartFile File) {
		String fileName=fileStorageService.storeBulkUploadFile(File);
		String fileDownloadUri=ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadBulkFile/")
				.path(fileName)
				.toUriString();
		
		return new UploadFileResponse(fileName,fileDownloadUri,File.getContentType(),File.getSize());
		
	}
	
	@PostMapping("/uploadMultipleFiles")
	@ApiOperation(value="Upload multiple files", notes="Uploads multiple Multipart Files and returns the download URI",produces = "application/json", nickname="uploadMultipleFiles")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile Files){
		return Arrays.asList(Files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
	}
	
	@GetMapping("/downloadFile/{fileName:.+}")
	@ApiOperation(value="Download the file based on filename given", notes="Download the file based on the filename given", nickname="downloadFile")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
	@GetMapping("/downloadBulkFile/{fileName:.+}")
	@ApiOperation(value="Download the file based on filename given", notes="Download the file based on the filename given", nickname="downloadFile")
	public ResponseEntity<Resource> downloadBulkFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadBulkUploadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
