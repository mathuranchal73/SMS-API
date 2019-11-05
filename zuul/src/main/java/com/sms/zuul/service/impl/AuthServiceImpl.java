package com.sms.zuul.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sms.zuul.model.User;
import com.sms.zuul.payload.JwtAuthenticationResponse;
import com.sms.zuul.payload.LoginRequest;
import com.sms.zuul.service.IAuthService;
import com.sms.zuul.exception.CustomZuulException;
import com.sms.zuul.repository.JwtTokenRepository;
import com.sms.zuul.repository.UserRepository;
import com.sms.zuul.security.JwtTokenProvider;
import com.sms.zuul.security.UserPrincipal;

@Service
public class AuthServiceImpl implements IAuthService {
	
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenRepository jwtTokenRepository;
    

	
	@Override
	public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
		
		        
		try {
			Authentication authentication = authenticationManager.authenticate(
	                 new UsernamePasswordAuthenticationToken(
	                         loginRequest.getUsernameOrEmail(),
	                         loginRequest.getPassword()
	                 )
	         );
        	 SecurityContextHolder.getContext().setAuthentication(authentication);
        	 String jwt = jwtTokenProvider.generateToken(authentication);
        	
        		 return(new JwtAuthenticationResponse(jwt));
        	 
             
		} catch (AuthenticationException e) {
            throw new CustomZuulException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
		}
        }


	@Override
	public Boolean logoutUser(String token) {
		// TODO Auto-generated method stub
		if(isValidToken(token))
		{
			return jwtTokenProvider.logoutToken(token);
		}
		
		return false;
	}

	@Override
	public Boolean isValidToken(String token) {
		 
		return jwtTokenProvider.validateToken(token);
    	
	}

	@Override
	public String createNewToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}


}