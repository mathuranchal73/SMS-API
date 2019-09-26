package com.sms.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;



@Service
public class Listener {

	 private static final Logger logger = LoggerFactory.getLogger(Listener.class);
	 

		
	 @Value("${tpd.topic-name}")
	 private static String TOPIC="Welcome_Email";

	   
	    
	   @Autowired
	   KafkaTemplate kafkaTemplate;
	    
	   
	    
	/**   @KafkaListener(id = "batch-listener", topics = "Welcome_Email")
	    public void listenMessage(String message) {

	        logger.info(String.format("#### -> Listening message -> %s", message));
	    } **/
}
