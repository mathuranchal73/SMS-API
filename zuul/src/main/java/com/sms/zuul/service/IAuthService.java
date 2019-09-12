package com.sms.zuul.service;

import com.sms.zuul.model.User;
import com.sms.zuul.payload.JwtAuthenticationResponse;
import com.sms.zuul.payload.LoginRequest;
import com.sms.zuul.security.UserPrincipal;

public interface IAuthService {
	
	JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);

    Boolean isValidToken(String token);

    String createNewToken(String token);
	Boolean logoutUser(String token);

}
