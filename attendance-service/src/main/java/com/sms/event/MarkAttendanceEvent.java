package com.sms.event;



import org.springframework.context.ApplicationEvent;

import com.sms.document.Attendance;
import com.sms.model.User;

public class MarkAttendanceEvent extends ApplicationEvent {
	
	private User user;
	private Attendance attendance;
	
	
	public MarkAttendanceEvent(User user, Attendance attendance) {
		super(user);
		this.user = user;
		this.attendance = attendance;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Attendance getAttendance() {
		return attendance;
	}


	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}
	
	
	

}
