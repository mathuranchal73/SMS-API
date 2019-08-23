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
	
	
	  

	@KafkaListener(id = "batch-listener", topics = "${kafka.topic-name}")
	  public ArrayList<MailObject> receive(List<String> data,
	      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
	      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
		  
	      ArrayList<MailObject> mailObjectList=new ArrayList<>();
	    LOGGER.info("start of batch receive");
	    for (int i = 0; i < data.size(); i++) {
	      LOGGER.info("received message='{}' with partition-offset='{}'", data.get(i),
	          partitions.get(i) + "-" + offsets.get(i));
	      
	
	      // handle message
	      try {
			MailObject mailObject= this.objectMapper.readValue(data.get(i), MailObject.class);
			mailObjectList.add(mailObject);
		} catch (JsonParseException e) {
			LOGGER.error(data.get(i),e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			LOGGER.error(data.get(i),e);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error(data.get(i),e);
			e.printStackTrace();
		}	     
	    }
	    LOGGER.info("end of batch receive");
	    
	   sendMailToList(mailObjectList);
	   
	   return mailObjectList;
	  
	  }
	  
	  public void sendMailToList(ArrayList<MailObject> mailObjectList) {
		  
		  	Application application = eurekaClient.getApplication(emailServiceServiceId);
			InstanceInfo instanceInfo = application.getInstances().get(0);
			String url = "http://"+instanceInfo.getIPAddr()+ ":"+instanceInfo.getPort()+"/sendMail";
			
			System.out.println(url);
			
			 //set up the basic authentication header 
	       String authorizationHeader = "Basic ";
	        //setting up the request headers
	        HttpHeaders requestHeaders = new HttpHeaders();
	        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
	        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        requestHeaders.add("Authorization", authorizationHeader);
			
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

}
	  
	  
}
