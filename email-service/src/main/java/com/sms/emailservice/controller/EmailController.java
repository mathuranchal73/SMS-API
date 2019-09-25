package com.sms.emailservice.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sms.emailservice.model.MailObject;
import com.sms.emailservice.service.EmailServiceImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/mail")
@Api(value="mail", description = "Email Service", tags=("mails"))
public class EmailController {

    
    @Autowired
    EmailServiceImpl emailService;    
    
    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    public ResponseEntity<?> createMailWithTemplate()throws Exception  {
      
        emailService.sendSimpleMessage();

        return new ResponseEntity<>("Email Sent Successfully to",HttpStatus.OK);
    }
    
   
}
