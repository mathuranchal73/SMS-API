package com.sms.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.sms.model.AcademicSession;

public class CreateStudentRequest {
	
	private String firstName;
	
	private String lastName;
	
	private String dateOfAdmission;
	
	private String academicSessions;
	
	 private String studentEmail;
	
	private String parentEmail;
	
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfAdmission() {
		return dateOfAdmission;
	}

	public void setDateOfAdmission(String dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}

	public String getAcademicSessions() {
		return academicSessions;
	}

	public void setAcademicSessions(String academicSessions) {
		this.academicSessions = academicSessions;
	}

	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	public String getParentEmail() {
		return parentEmail;
	}

	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}
	
	
}
