package com.sms.uploadservice;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.sms.uploadservice.properties.FileStorageProperties;



@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties({FileStorageProperties.class})
public class UploadServiceApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(UploadServiceApplication.class, args);
	}

}

