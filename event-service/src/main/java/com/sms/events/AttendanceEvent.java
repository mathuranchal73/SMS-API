package com.sms.events;

import org.springframework.context.ApplicationEvent;

import com.sms.model.Attendance;
import com.sms.model.User;

public class AttendanceEvent extends ApplicationEvent  {
	
	private static final long serialVersionUID = 1L;

	private User user;
	private Attendance attendance;
	

	public AttendanceEvent(User user, Attendance attendance) {
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
