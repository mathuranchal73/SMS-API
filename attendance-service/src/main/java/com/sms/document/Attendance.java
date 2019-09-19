package com.sms.document;

import java.time.Instant;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sms.model.Student;

@Document(collection= "Attendance")
public class Attendance {

	 
	  private Student student;
	  private int date;
	  private Instant timeIn;
	  
	  
	
	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Attendance(Student student, int date, Instant timeIn) {
		super();
		this.student = student;
		this.date = date;
		this.timeIn = timeIn;
	}
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public Instant getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(Instant timeIn) {
		this.timeIn = timeIn;
	}

	
	  
}

