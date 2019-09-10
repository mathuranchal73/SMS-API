package com.sms.zuul.service;

import com.sms.zuul.model.User;
import com.sms.zuul.payload.JwtAuthenticationResponse;
import com.sms.zuul.payload.LoginRequest;

public interface IAuthService {
	
	JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);
	User saveUser(User user);

    boolean logout(String token);

    Boolean isValidToken(String token);

    String createNewToken(String token);

}
