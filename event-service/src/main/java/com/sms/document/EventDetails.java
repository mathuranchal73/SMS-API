package com.sms.document;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sms.model.Attendance;
import com.sms.model.User;

@Document(collection= "EventDetails")
public class EventDetails {
	
	private Attendance attendance;
	private User user;
	
	public Attendance getAttendance() {
		return attendance;
	}
	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	

}
