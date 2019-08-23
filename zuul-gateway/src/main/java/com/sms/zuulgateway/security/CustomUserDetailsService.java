package com.sms.zuulgateway.security;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sms.zuulgateway.exception.CustomException;

import com.sms.zuulgateway.bean.auth.User;
import com.sms.zuulgateway.bean.auth.DbUserDetails;
import com.sms.zuulgateway.bean.auth.Role;
import com.sms.zuulgateway.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	    @Autowired
	    private UserRepository userRepository;
	    
	    @Override
	    @Transactional
	    public UserDetails loadUserByUsername(String usernameOrEmail)
	            throws UsernameNotFoundException {
	        // Let people login with either username or email
	        User user = userRepository.findByUsername(usernameOrEmail);
	        if (user == null || user.getRoles() == null || user.getRoles().isEmpty()) {
	            throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
	        }
	        
	        String [] authorities = new String[user.getRoles().size()];    
	        
	        int count=0;
	        for (Role role : user.getRoles()) {
	            //NOTE: normally we dont need to add "ROLE_" prefix. Spring does automatically for us.
	            //Since we are using custom token using JWT we should add ROLE_ prefix
	        	authorities[count] = "ROLE_"+role.getRole();
	            count++;
	        }
	        DbUserDetails userDetails = new DbUserDetails(user.getId(),user.getName(),user.getEmail(),user.getUsername(),user.getPassword(),user.getActive(),
	                user.isLocked(), user.isExpired(),user.isEnabled(),authorities);
	        return userDetails;

	    }

}
