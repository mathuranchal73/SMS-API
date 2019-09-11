package com.sms.zuul.security;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sms.zuul.model.User;
import com.sms.zuul.repository.UserRepository;
import com.sms.zuul.security.UserPrincipal;
import com.sms.zuul.exception.ResourceNotFoundException;
import com.sms.zuul.exception.CustomZuulException;




@Service
public class CustomUserDetailsService  implements UserDetailsService {
	
	private Converter<User, UserDetails> userUserDetailsConverter;
	
	@Autowired
	private UserRepository userRepository;

	@Override
    @Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		  // Let people login with either username or email
        User user = userRepository.findByUsername(usernameOrEmail)
        		  .orElseThrow(() ->
                  new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
        				  );

        if (user == null ) {
            throw new CustomZuulException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        }
        
        return userUserDetailsConverter.convert(user);
	}

	 @Transactional
	    public UserDetails loadUserById(Long id) {
	        User user = userRepository.findById(id).orElseThrow(
	            () -> new ResourceNotFoundException("User", "id", id)
	        );

	        return UserPrincipal.create(user);
	    }

	
}