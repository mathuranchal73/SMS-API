package com.sms.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.payload.EmailRequest;
import com.sms.payload.MailObject;
import com.sms.security.CurrentUser;
import com.sms.security.UserPrincipal;
import com.sms.service.IEmailService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/v1/email")
@Api(value="email-api", description = "Data service operations for Email", tags=("email"))
public class EmailController {
	
	private static Logger logger= LoggerFactory.getLogger("EmailController.class");
	
	@Autowired
    IEmailService emailService; 
	
	@PostMapping("/send")
	@PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> sendSimpleMail(@RequestHeader("Authorization") String token,@CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody EmailRequest emailRequest)throws Exception  {
      
        return emailService.sendSimpleMessage(token,currentUser,emailRequest);
       
    }
	
	@PostMapping("/sendVerificationMail")
	@PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> sendVerificationMail(@RequestHeader("Authorization") String token,@CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody MailObject mailObject)throws Exception  {
      
        return emailService.sendVerificationMail(token,currentUser,mailObject);
       
    }
	

}
