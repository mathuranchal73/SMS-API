package com.sms.zuul.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import com.sms.zuul.payload.JwtAuthenticationResponse;
import com.sms.zuul.payload.LoginRequest;
import com.sms.zuul.service.IAuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api")
@Api(value="zuul", description = "Data service operations on Zuul", tags=("zuul"))
public class AuthController {
	
	@Autowired
	private IAuthService iAuthService;
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	
    @PostMapping("/signin")
    @ApiOperation(value="Signin", notes="",produces = "application/json", nickname="signin")
    public JwtAuthenticationResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

         return iAuthService.authenticateUser(loginRequest);
    }
    
	

}
