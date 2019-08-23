package com.sms.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MailObjectMapper {
	
	private final ObjectMapper objectMapper;
	private static final Logger logger= LoggerFactory.getLogger(MailObjectMapper.class);
	
	@Autowired
	public MailObjectMapper(ObjectMapper objectMapper) {
		super();
		this.objectMapper=objectMapper;
	}
	

}
