package com.sms.zuul.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;


import com.sms.zuul.payload.JwtAuthenticationResponse;
import com.sms.zuul.payload.LoginRequest;
import com.sms.zuul.payload.LogoutResponse;
import com.sms.zuul.security.UserPrincipal;
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
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

         return ResponseEntity.ok(iAuthService.authenticateUser(loginRequest));
    }
    
    @PostMapping("/signout")
    @ApiOperation(value="Signout", notes="",produces = "application/json", nickname="signout")
    public ResponseEntity<LogoutResponse> invalidateUser(@RequestHeader(value="Authorization") String token) {
    	 HttpHeaders headers = new HttpHeaders();
    	if(iAuthService.logoutUser(token))
    	{
    		 headers.remove("Authorization");
    		return new ResponseEntity(new LogoutResponse(true, "User successfully Logged out"), HttpStatus.ACCEPTED);
    	}
    	return new ResponseEntity(new LogoutResponse(false, "Error encountered in Logging out User"), HttpStatus.NOT_MODIFIED);
    }
    
}
