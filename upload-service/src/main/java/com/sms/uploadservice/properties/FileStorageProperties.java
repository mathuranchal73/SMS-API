package com.sms.uploadservice.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="file")
public class FileStorageProperties {
	private static String uploadDir;
	private static String bulkUploadDir;

	public static String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		FileStorageProperties.uploadDir = uploadDir;
	}
	
	public static String getBulkUploadDir() {
		return uploadDir;
	}
	public void setBulkUploadDir(String bulkUploadDir) {
		FileStorageProperties.bulkUploadDir = bulkUploadDir;
	}

}
