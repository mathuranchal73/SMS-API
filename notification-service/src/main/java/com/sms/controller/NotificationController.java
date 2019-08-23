package com.sms.controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.document.GlobalMessage;
import com.sms.service.GlobalMessageServiceImpl;


@RestController
@RequestMapping("/v1/notification")
public class NotificationController {
	
	 protected Logger logger = LoggerFactory.getLogger(NotificationController.class);
	 
	@Autowired
	GlobalMessageServiceImpl globalMessageService;
	
	@PostMapping("/sendGlobalMessage")
	   public ResponseEntity<?> send(@RequestBody @Valid GlobalMessage globalMessage) {
	     
		  try 
		  {
			 globalMessageService.send(globalMessage);
			 logger.info("Global Message for "+globalMessage.getStudent().getId()+" Saved to Mongo");
			 return new ResponseEntity<>(HttpStatus.OK);
		  } 
		  catch (Exception e) {
			e.printStackTrace();
			 return new ResponseEntity<>(e,HttpStatus.UNPROCESSABLE_ENTITY);
		}
		  
		 
	   }

}
