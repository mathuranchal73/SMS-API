package com.sms.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.model.Attendance;
import com.sms.events.AttendanceEvent;
import com.sms.model.User;
import com.sms.producer.KafkaAttendanceProducer;

public class AttendanceEventListener implements ApplicationListener<AttendanceEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(AttendanceEventListener.class);
	
	@Value("${tbd.kafka.message")
	 private String MarkedAttendanceMessage;
	 
	 @Autowired
	 KafkaAttendanceProducer producer;
	 
	 @Autowired
	 private ObjectMapper objectMapper;
	 
	 @Override
		public void onApplicationEvent(AttendanceEvent event) {
			this.sendNotification(event);
			
		}

		private void sendNotification(AttendanceEvent event) {
			
			
			try {
				String jsonString=objectMapper.writeValueAsString(event);
				producer.sendMessage(jsonString);
				logger.info("Message sent to Kafka for Student: "+event.getAttendance().getStudent().getRollNo());
			} catch (Exception e) {
				logger.error("Encountered Exception in sending message to Kafka for Student: "+event.getAttendance().getStudent().getRollNo(),e);
				e.getMessage();
			}

			
			
		}

}
