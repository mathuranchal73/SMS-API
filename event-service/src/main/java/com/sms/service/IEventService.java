package com.sms.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.sms.payload.EventRequest;
import com.sms.security.UserPrincipal;

public interface IEventService {

	ResponseEntity<?> postKafka(String token, UserPrincipal currentUser, @Valid EventRequest eventRequest);

}
