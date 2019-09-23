package com.sms.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sms.document.Event;
import com.sms.events.AttendanceEvent;
import com.sms.payload.ApiResponse;
import com.sms.payload.EventRequest;
import com.sms.repository.EventRepository;
import com.sms.security.UserPrincipal;

@Service
public class EventServiceImpl implements IEventService {
	
    @Autowired
	private ApplicationEventPublisher eventPublisher;
    
    @Autowired
    EventRepository eventRepository;

	@Override
	public ResponseEntity<?> postKafka(String token, UserPrincipal currentUser, @Valid EventRequest eventRequest) {
	
			try {
				eventPublisher.publishEvent(new AttendanceEvent(eventRequest.getUser(),eventRequest.getAttendance()));
				return new ResponseEntity<>(new ApiResponse(true,"Event Completed"),HttpStatus.OK);
				
			} catch (Exception e) {
				return new ResponseEntity<>(new ApiResponse(true,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
			}

	}

}
