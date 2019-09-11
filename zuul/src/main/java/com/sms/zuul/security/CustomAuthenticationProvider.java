package com.sms.zuul.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sms.zuul.model.User;
import com.sms.zuul.repository.UserRepository;

@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider{
	
	 private final UserRepository userRepository;
	 
	    @SuppressWarnings("unused")
		private UserDetailsService userDetailsService;

	    public CustomAuthenticationProvider(UserRepository userRepository, UserDetailsService userDetailsService){
	    	super();
	    	this.setUserDetailsService(userDetailsService);
	    	this.userRepository = userRepository;
	    }

	    @Override
	    public Authentication authenticate(Authentication auth) throws AuthenticationException {
		        final User user = userRepository.findByUsername(auth.getPrincipal().toString())
	        		.orElseThrow(() ->
	                  new UsernameNotFoundException("User not found with username or email : " + auth.getName())
	        				  );
	        if ((user == null)) {
	            throw new BadCredentialsException("Invalid username or password###");
	        }
	        final Authentication result = super.authenticate(auth);
	        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
	    }

	    @Override
	    public boolean supports(Class<?> authentication) {
	        return authentication.equals(UsernamePasswordAuthenticationToken.class);
	    }

}
