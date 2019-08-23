package com.sms.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sms.model.Student;

@Document(collection= "GlobalMessage")
public class GlobalMessage {

	 
	  private Student student;
	  private String message;
	  
	  
	public GlobalMessage(Student student, String message) {
		super();
		
		this.student = student;
		this.message= message;
	}
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	  
	  
}
