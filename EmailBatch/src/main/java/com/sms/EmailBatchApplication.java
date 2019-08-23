package com.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.sms.kafka.Receiver;

@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class EmailBatchApplication {
		
	Receiver receiver= new Receiver();
	public static void main(String[] args) {
		SpringApplication.run(EmailBatchApplication.class, args);
		
	}
	
	@Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
	
	
}
