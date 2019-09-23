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

import com.sms.payload.EventRequest;
import com.sms.security.CurrentUser;
import com.sms.security.UserPrincipal;
import com.sms.service.EventServiceImpl;
import com.sms.service.IEventService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/v1/event")
@Api(value="event", description = "Data service operations for Event Service", tags=("event"))
public class EventController {

	private static Logger logger= LoggerFactory.getLogger("EventController.class");
	
	@Autowired
	IEventService eventService;
	
	 @PostMapping("/postAttendanceKafka")
	 @PreAuthorize("hasRole('TEACHER')")
	 public ResponseEntity<?> postKafka(@RequestHeader("Authorization") String token,@CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody EventRequest eventRequest){
		 
		return eventService.postKafka(token,currentUser,eventRequest);
		
	}
}
