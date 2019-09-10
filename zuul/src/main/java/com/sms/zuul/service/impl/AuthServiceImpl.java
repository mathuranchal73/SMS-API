package com.sms.zuul.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

@Service
public class AuthServiceImpl implements IAuthService {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
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
			/**Authentication authentication = authenticationManager.authenticate(
	                 new UsernamePasswordAuthenticationToken(
	                         loginRequest.getUsernameOrEmail(),
	                         loginRequest.getPassword()
	                 )
	         );
        	 SecurityContextHolder.getContext().setAuthentication(authentication);**/
        	  User user = userRepository.findByUsername(loginRequest.getUsernameOrEmail())
        			  .orElseThrow(() ->
              new UsernameNotFoundException("User not found with username or email : " + loginRequest.getUsernameOrEmail())
    				  );
        	 if (user == null || user.getRoles() == null || user.getRoles().isEmpty()) {
                 throw new CustomZuulException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
             }
        	 //String jwt = jwtTokenProvider.generateToken(authentication);
             //return new JwtAuthenticationResponse(jwt);
             return null;
             
		} catch (AuthenticationException e) {
            throw new CustomZuulException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
		}
        }


	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean logout(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean isValidToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNewToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
