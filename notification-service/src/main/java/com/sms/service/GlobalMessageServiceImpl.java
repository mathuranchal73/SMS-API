package com.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.document.GlobalMessage;
import com.sms.repository.GlobalMessageRepository;

@Service
public class GlobalMessageServiceImpl implements IGlobalMessageService {
	
	@Autowired
	GlobalMessageRepository globalMessageRepository;

	@Override
	public void send(GlobalMessage globalMessage) {
		
		globalMessageRepository.save(globalMessage);
		
	}

}
