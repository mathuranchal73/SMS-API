package com.sms.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaAttendanceProducer {
	
	 private static final Logger logger = LoggerFactory.getLogger(KafkaAttendanceProducer.class);
	 
	 @Value("${tpd.topic-name}")
	 private static String TOPIC="Welcome_Email";

	   
	    
	 @Autowired
	 KafkaTemplate kafkaTemplate;
	    

	 public synchronized void sendMessage(String message) {
	   	
            ProducerRecord<String, String> rec = new ProducerRecord<String, String>(TOPIC,message);

	        logger.info(String.format("#### -> Producing Kafka message -> %s for TOPIC -> %s", message,TOPIC));
	        this.kafkaTemplate.send(rec);
	     
	    }

}
