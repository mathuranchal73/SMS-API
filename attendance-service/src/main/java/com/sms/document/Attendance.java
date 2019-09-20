package com.sms.document;

import java.time.Instant;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sms.model.Student;
import com.sms.model.audit.UserDateAudit;

@Document(collection= "Attendance")
public class Attendance {

	private static final long serialVersionUID = 1L;
	private Student student;
	  private String dateOfAttendance;
	  private Instant timeIn;
	  
	  
	
	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Attendance(Student student, String dateOfAttendance, Instant timeIn) {
		super();
		this.student = student;
		this.dateOfAttendance = dateOfAttendance;
		this.timeIn = timeIn;
	}


	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	

	public String getDateOfAttendance() {
		return dateOfAttendance;
	}


	public void setDateOfAttendance(String string) {
		this.dateOfAttendance = string;
	}


	public Instant getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(Instant timeIn) {
		this.timeIn = timeIn;
	}

	
	  
}

