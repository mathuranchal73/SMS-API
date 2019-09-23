package com.sms.model;

import java.time.Instant;

public class Attendance {
	
	private Student student;
	  private String dateOfAttendance;
	  private Instant timeIn;
	  
	  
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getDateOfAttendance() {
		return dateOfAttendance;
	}
	public void setDateOfAttendance(String dateOfAttendance) {
		this.dateOfAttendance = dateOfAttendance;
	}
	public Instant getTimeIn() {
		return timeIn;
	}
	public void setTimeIn(Instant timeIn) {
		this.timeIn = timeIn;
	}
	  
	  

}
