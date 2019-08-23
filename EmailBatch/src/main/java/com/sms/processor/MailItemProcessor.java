package com.sms.processor;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.sms.model.MailObject;

@Component
public class MailItemProcessor implements ItemProcessor<MailObject, MailObject>{
//public class MailItemProcessor {

	 @Autowired
	    private RestTemplate restTemplate;
	
	  @Autowired
	    private EurekaClient eurekaClient;
	
	@Value("${service.email-service.serviceId}")
    private String emailServiceServiceId;
	
	private static final Logger log = LoggerFactory.getLogger(MailItemProcessor.class);


	//public MailObject process(final MailObject item) throws Exception {
		public void process(String text) throws Exception {
		
		Application application = eurekaClient.getApplication(emailServiceServiceId);
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+ ":"+instanceInfo.getPort()+"/mail"+"/send";
		
		System.out.println(url);
		
		 //set up the basic authentication header 
       String authorizationHeader = "Basic ";
        //setting up the request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", authorizationHeader);
		
       // HttpEntity<MailObject> requestEntity = new HttpEntity<>(item, requestHeaders);
        
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("toEmail", "mathuranchal73@gmail.com");
        bodyMap.add("Subject", "test");
        bodyMap.add("text", text);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
		
		
		try {
			System.out.println("Running RestTemplate");
			//restTemplate.exchange(url, HttpMethod.POST, requestEntity, MailObject.class);
			System.out.println(requestEntity.getBody());
			 ResponseEntity<String> response = restTemplate.exchange(url,
		                HttpMethod.POST, requestEntity, String.class);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Email Sent");
		
		//final String toemail = item.gettoemail().toUpperCase();
        //final String subject = item.getSubject().toUpperCase();
        //final String text= item.getText().toUpperCase();
        
       // final MailObject transformedMail = new MailObject(toemail, subject,text);
        //log.info("Converting (" + item + ") into (" + transformedMail + ")");
		// TODO Auto-generated method stub
		
		
		//return item;
	}


		@Override
		public MailObject process(MailObject item) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	
	
}
