package com.sms.payload;

import java.time.Instant;

import com.sms.document.Attendance;
import com.sms.model.User;

public class AttendanceEventObject {
	
	private Attendance attendance;
	private User user;
	private Instant timestamp;
	private String message;
	
	

	public AttendanceEventObject(Attendance attendance, User user, Instant timestamp, String message) {
		super();
		this.attendance = attendance;
		this.user = user;
		this.timestamp = timestamp;
		this.message = message;
	}
	
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
	public Instant getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
