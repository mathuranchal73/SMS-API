package com.sms.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.document.Attendance;
import com.sms.kafka.Producer;
import com.sms.model.User;
import com.sms.payload.AttendanceEventObject;

public class AttendanceEventListener implements ApplicationListener<MarkAttendanceEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(AttendanceEventListener.class);
	
	 @Value("${tbd.kafka.message")
	 private String MarkedAttendanceMessage;
	 
	 @Autowired
	 Producer producer;
	 
	 @Autowired
	 private ObjectMapper objectMapper;

	@Override
	public void onApplicationEvent(MarkAttendanceEvent event) {
		this.sendNotification(event);
		
	}

	private void sendNotification(MarkAttendanceEvent event) {
		User user= event.getUser();
		Attendance attendance= event.getAttendance();
		
		AttendanceEventObject attendanceEventObject= new AttendanceEventObject(attendance,user,java.time.Instant.now(),MarkedAttendanceMessage);
		
		try {
			String jsonString=objectMapper.writeValueAsString(attendanceEventObject);
			producer.sendMessage(jsonString);
			logger.info("Message sent to Kafka for Student: "+attendanceEventObject.getAttendance().getStudent().getRollNo());
		} catch (Exception e) {
			logger.error("Encountered Exception in sending message to Kafka for Student: "+attendanceEventObject.getAttendance().getStudent().getRollNo(),e);
			e.getMessage();
		}

		
		
	}
	

}
