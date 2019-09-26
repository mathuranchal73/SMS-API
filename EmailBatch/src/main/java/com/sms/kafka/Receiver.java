package com.sms.kafka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.sms.model.MailObject;

@Service
public class Receiver {

	 private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	 
	 @Autowired
	 ObjectMapper objectMapper;
	 

	 @Autowired
	 private RestTemplate restTemplate;
	
	  @Autowired
	 private EurekaClient eurekaClient;
	
	@Value("${service.email-service.serviceId}")
    private String emailServiceServiceId;
	
	@KafkaListener(id = "batch-listener", topics = "amazingTopic")
	  public ArrayList<MailObject> receive(String data,
	      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
	      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
		  
		 LOGGER.info("start of batch receive");
		 
	      ArrayList<MailObject> mailObjectList=new ArrayList<>();
	      
	  /**  for (int i = 0; i < data.size(); i++) {
	      LOGGER.info("received message='{}' with partition-offset='{}'", data.get(i),
	          partitions.get(i) + "-" + offsets.get(i));
	      **/
	
	      // handle message
	      try {
			MailObject mailObject= this.objectMapper.readValue(data, MailObject.class);
			mailObjectList.add(mailObject);
		} catch (JsonParseException e) {
			LOGGER.error(data,e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			LOGGER.error(data,e);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error(data,e);
			e.printStackTrace();
		}	     
	  

	    String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiYXV0aCI6eyJpZCI6MiwibmFtZSI6Ik5lZWxhbSBNYXRodXIiLCJ1c2VybmFtZSI6Im5lZWxhbSIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX1RFQUNIRVIifV0sImVuYWJsZWQiOnRydWUsImFjY291bnROb25FeHBpcmVkIjp0cnVlLCJjcmVkZW50aWFsc05vbkV4cGlyZWQiOnRydWUsImFjY291bnROb25Mb2NrZWQiOnRydWV9LCJpYXQiOjE1Njk0ODE3MzksImV4cCI6MTU3MDA4NjUzOX0.KgExm7c1X6nGALbJaqO4eyN89jXsTrcMfzEW7QLo09AFy0JnhQDvhIP_OkgIhYWU3COdd82d2rEi5gzZ-2LYag";
		  

	    sendMailToList(token,mailObjectList);
	   
	   return mailObjectList;
	  
	  }
	  
	  public void sendMailToList(String token,ArrayList<MailObject> mailObjectList) {
		  	Application application = eurekaClient.getApplication(emailServiceServiceId);
			InstanceInfo instanceInfo = application.getInstances().get(0);
			String url = "http://"+instanceInfo.getIPAddr()+ ":"+instanceInfo.getPort()+"/v1/email/sendVerificationMail";
			
			System.out.println(url);

	        //setting up the request headers
	        HttpHeaders requestHeaders = new HttpHeaders();
	        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
	        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        requestHeaders.add("Authorization", token);
			
	        for(MailObject i:mailObjectList)
	        {
	        	HttpEntity<MailObject> requestEntity = new HttpEntity<>(i, requestHeaders);
	        	try {
	    			LOGGER.info("Hitting email-service for User-"+i.getUser_uuid());
	    			ResponseEntity<?> response= restTemplate.exchange(url, HttpMethod.POST, requestEntity, MailObject.class);
	    			LOGGER.info("Email Sent"+response.getStatusCodeValue());
	        	} catch (Exception e) {
	    			LOGGER.error("Exception Encountered",e);
	    		}
	    		
	    			
	        }
	        
	        LOGGER.info("Email sent to Complete EmailObject list");
	        System.exit(0);
}
	  
	  
}
