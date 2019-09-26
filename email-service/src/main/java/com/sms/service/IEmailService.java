package com.sms.service;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sendgrid.Response;
import com.sms.payload.EmailRequest;
import com.sms.payload.MailObject;
import com.sms.security.UserPrincipal;

public interface IEmailService {

	ResponseEntity<?> sendSimpleMessage(String token, UserPrincipal currentUser, @Valid EmailRequest emailRequest) throws IOException;

	ResponseEntity<?> sendVerificationMail(String token, UserPrincipal currentUser, @Valid MailObject mailObject);

}
