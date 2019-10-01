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
import com.sms.payload.LoginRequest;
import com.sms.payload.AuthResponse;

@Service
public class Receiver {

	 private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	 
	 @Autowired
	 ObjectMapper objectMapper;
	 

	 @Autowired
	 private RestTemplate restTemplate;
	
	  @Autowired
	 private EurekaClient eurekaClient;
	
	@Value("${app.system.username}")
	private String username;
	  
	@Value("${app.system.password}")
	private String password;
	    
	@Value("${service.email-service.serviceId}")
    private String emailServiceServiceId;
	
	@Value("${service.zuul.serviceId}")
    private String zuulServiceId;
	
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

	    String token= getAuthToken(username,password);
	    sendMailToList(token,mailObjectList);
	   
	   return mailObjectList;
	  
	  }
	  
	  private synchronized String getAuthToken(String usernameOrEmail, String password2) {
		  
		  LoginRequest systemLogin= new LoginRequest(usernameOrEmail,password2);
		  
		  	Application application = eurekaClient.getApplication(zuulServiceId);
			InstanceInfo instanceInfo = application.getInstances().get(0);
			 HttpHeaders requestHeaders = new HttpHeaders();
		     requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		     requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			
			HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(systemLogin,requestHeaders);
			
			String url = "http://"+instanceInfo.getIPAddr()+ ":"+instanceInfo.getPort()+"/api/signin";

			ResponseEntity<AuthResponse> response= restTemplate.exchange(url, HttpMethod.POST, requestEntity, AuthResponse.class);
			System.out.println("Auth Token: "+response.getBody().getAccessToken());
			return response.getBody().getAccessToken();
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
	    			LOGGER.info("Hitting email-service for User-"+i.getUser().getUuid());
	    			LOGGER.info(requestEntity.getBody().toString());
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
