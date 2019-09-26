package com.sms.service;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sms.payload.EmailRequest;
import com.sms.payload.MailObject;
import com.sms.security.UserPrincipal;

@Service
public class EmailServiceImpl implements IEmailService {
	
	@Value("${app.sendGrid_secret")
	private String secret;

	@Override
	public ResponseEntity<?> sendSimpleMessage(String token, UserPrincipal currentUser, @Valid EmailRequest emailRequest) throws IOException {
		
		 Email from = new Email(emailRequest.getFrom_email());
		 String subject = emailRequest.getSubject();
		 Email to = new Email(emailRequest.getTo_email());
		 Content content = new Content("text/plain", emailRequest.getBody());
		 Mail mail = new Mail(from, subject, to, content);
		  SendGrid sg = new SendGrid("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");
		  Request request = new Request();
		  try {
			  request.setMethod(Method.POST);
		      request.setEndpoint("mail/send");
		      request.setBody(mail.build());
		      Response response = sg.api(request);
		      
		      return new ResponseEntity<>(response,HttpStatus.OK);
			  
   } catch (IOException ex) {
   	 return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
   }
	}

	@Override
	public ResponseEntity<?> sendVerificationMail(String token, UserPrincipal currentUser,
			@Valid MailObject mailObject) {
		
		 Email from = new Email("mathuranchal90@gmail.com");
		 String subject = "Verify your Account";
		 Email to = new Email(mailObject.getEmail());
		 Content content = new Content("text/plain", mailObject.getVerificationLink().toString());
		 Mail mail = new Mail(from, subject, to, content);
		  SendGrid sg = new SendGrid("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");
		  Request request = new Request();
		  try {
			  request.setMethod(Method.POST);
		      request.setEndpoint("mail/send");
		      request.setBody(mail.build());
		      Response response = sg.api(request);
		      
		      return new ResponseEntity<>(response,HttpStatus.OK);
			  
  } catch (IOException ex) {
  	 return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
  }
	}
}
