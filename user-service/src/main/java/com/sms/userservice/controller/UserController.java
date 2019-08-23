package com.sms.userservice.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;




import com.sms.userservice.exception.ResourceNotFoundException;
import com.sms.userservice.model.UserProfile;
import com.sms.userservice.security.CurrentUser;
import com.sms.userservice.security.UserPrincipal;
import com.sms.userservice.payload.UserSummary;
import com.sms.userservice.exception.AppException;
import com.sms.userservice.model.Role;
import com.sms.userservice.model.RoleName;
import com.sms.userservice.model.User;
import com.sms.userservice.payload.ApiResponse;
import com.sms.userservice.payload.SignUpRequest;
import com.sms.userservice.payload.UserIdentityAvailability;
import com.sms.userservice.repository.RoleRepository;
import com.sms.userservice.repository.UserRepository;
import com.sms.userservice.security.JwtTokenProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	  

	  	@Autowired
	    AuthenticationManager authenticationManager;

	    @Autowired
	    UserRepository userRepository;

	    @Autowired
	    RoleRepository roleRepository;

	    @Autowired
	    PasswordEncoder passwordEncoder;

	    @Autowired
	    JwtTokenProvider tokenProvider;
	    
	   
	    
	    @PostMapping("/signup")
	    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
	        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
	            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
	                    HttpStatus.BAD_REQUEST);
	        }

	        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
	            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
	                    HttpStatus.BAD_REQUEST);
	        }

	        // Creating user's account
	        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
	                signUpRequest.getEmail(), signUpRequest.getPassword());

	        user.setPassword(passwordEncoder.encode(user.getPassword()));

	        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
	                .orElseThrow(() -> new AppException("User Role not set."));

	        user.setRoles(Collections.singleton(userRole));

	        User result = userRepository.save(user);
	       

	        URI location = ServletUriComponentsBuilder
	                .fromCurrentContextPath().path("/api/users/{username}")
	                .buildAndExpand(result.getUsername()).toUri();

	        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	    }
	    @GetMapping("/me")
	    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
	        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
	        return userSummary;
	    }

	    @GetMapping("/{username}")
	    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

	        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName());

	        return userProfile;
	    }
	    @GetMapping("/checkUsernameAvailability")
	    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
	        Boolean isAvailable = !userRepository.existsByUsername(username);
	        return new UserIdentityAvailability(isAvailable);
	    }
	    
	    @GetMapping("/checkEmailAvailability")
	    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
	        Boolean isAvailable = !userRepository.existsByEmail(email);
	        return new UserIdentityAvailability(isAvailable);
	    }
	    
	    
	    
	    
	    
}
