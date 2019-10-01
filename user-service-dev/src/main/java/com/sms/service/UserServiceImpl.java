package com.sms.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sms.Person;
//import com.sms.security.JwtTokenProvider;
import com.sms.exception.AppException;
import com.sms.model.Role;
import com.sms.model.RoleName;
import com.sms.model.User;
import com.sms.model.VerificationToken;
import com.sms.payload.ApiResponse;
import com.sms.payload.SignUpRequest;
import com.sms.payload.UserIdentityAvailability;
import com.sms.repository.RoleRepository;
import com.sms.repository.TokenRepository;
import com.sms.repository.UserRepository;
import com.sms.security.JwtTokenProvider;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
    AuthenticationManager authenticationManager;

 	@Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
   /** String booststrapServers="127.0.0.1:9092";
    
    
    //Create Producer Properties
   
   
    
    
    Properties properties= new Properties();
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,booststrapServers);
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
    

    KafkaProducer<String,String> producer= new KafkaProducer<>(properties);
    
    ProducerRecord<String,String> record= new ProducerRecord<>("Welcome_Email","Hello World");
    
    producer.send(record, new Callback() {
    	public void onCompletion
    })
	**/

	@Override
	public UserIdentityAvailability checkUsernameAvailability(String username) {
		 Boolean isAvailable;
		 
		try {
			isAvailable = !userRepository.existsByUsername(username);
			
			return new UserIdentityAvailability(isAvailable);
			
		} catch (Exception e) 
		{
			logger.error("Exception raised checkUsernameAvailability REST Call {0}", e);
			e.printStackTrace();
			return null;
		}
		
		 
	}

	@Override
	public UserIdentityAvailability checkEmailAvailability(String email) {
		Boolean isAvailable;
		try {
			isAvailable = !userRepository.existsByEmail(email);
			return new UserIdentityAvailability(isAvailable);
			
		} catch (Exception e) 
		{
			logger.error("Exception raised checkEmailAvailability REST Call {0}", e);
			e.printStackTrace();
			return null;
		}
	}
    
	
	public VerificationToken createVerificationToken(User user, String token) {
		VerificationToken newUserToken = new VerificationToken(token, user);
		return tokenRepository.save(newUserToken);
	}
	 
	@Override
	@Transactional
	public VerificationToken getVerificationToken(String verificationToken) {
		return tokenRepository.findByToken(verificationToken);
	}

	@Override
	@Transactional
	public void enableRegisteredUser(User user) {
		if(!user.isEnabled())
		{
		user.setEnabled(true);
		userRepository.save(user);
	}
		else
			return;

}

	@Override
	public void exportUser(HttpServletResponse response) {
		 String filename = "users.csv";

	       
	        
	        try {
	        	
	        	
	        	//create a csv writer
	        	 StatefulBeanToCsv<User> writer = new StatefulBeanToCsvBuilder<User>(response.getWriter())
		                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
		                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
		                .withOrderedResults(false)
		                .build();
	        	 response.reset();
	        	 response.setContentType("text/csv");
	 	        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
	 	                "attachment; filename=\"" + filename + "\"");
				writer.write(userRepository.findAll());
				//return new ResponseEntity<>(new ApiResponse(true,"User Exported Successfully"),HttpStatus.OK);
				
			} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
				//return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ColumnPositionMappingStrategy setColumnMapping()
	{
		ColumnPositionMappingStrategy strategy= new ColumnPositionMappingStrategy();
		strategy.setType(Person.class);
		//String[] columns= new String[] {"gender","nationality","income","currency"};
		//strategy.setColumnMapping(columns);
		return strategy;
	}

	
}
