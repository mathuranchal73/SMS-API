package com.sms.event;

import java.net.URI;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.kafka.Producer;
import com.sms.model.User;
import com.sms.model.VerificationToken;
import com.sms.payload.MailObject;
import com.sms.service.UserServiceImpl;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {
	
	private static Logger logger= LoggerFactory.getLogger(RegistrationEmailListener.class);
	
	@Autowired
	private UserServiceImpl userService;
	
	 @Autowired
	 Producer producer;
	 
	 @Autowired
	 private ObjectMapper objectMapper;

	@Override
	public void onApplicationEvent(OnRegistrationSuccessEvent event) {
		this.confirmRegistration(event);	
	}

	private void confirmRegistration(OnRegistrationSuccessEvent event) {
		
		User user=event.getUser();
		String token= UUID.randomUUID().toString();
		VerificationToken verificationToken=userService.createVerificationToken(user, token);
		
		String recipient = user.getEmail();
		String subject = "Registration Confirmation";
		
		/**URI url = ServletUriComponentsBuilder
                .fromCurrentContextPath().path(event.getAppUrl()+"/v1/user/confirmRegistration?token={token}")
                .buildAndExpand(token).toUri();**/
		
		URI url = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/v1/user/confirmRegistration?token={token}")
                .buildAndExpand(token).toUri();
		
		MailObject userMailObject= new MailObject(user.getName(),user.getEmail(),url,verificationToken.getExpiryDate(),user.getUuid());
		
		
		
		try {
			String jsonString=objectMapper.writeValueAsString(userMailObject);
			producer.sendMessage(jsonString);
			logger.info("Message sent to Kafka for User"+userMailObject.getUser_uuid());
		} catch (Exception e) {
			logger.error("Encountered Exception for User-"+userMailObject.getUser_uuid(),e);
			e.printStackTrace();
		}
		
	}
	

}
