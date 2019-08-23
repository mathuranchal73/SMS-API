package com.sms.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.sms.model.AcademicSession;

public class CreateStudentRequest {
	
	private String firstName;
	
	private String lastName;
	
	private String doa;
	
	private String academicSessions;
	
	 private String studentEmail;
	
	private String parentEmail;
	
	private boolean enabled;
	
	

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

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


	public String getDoa() {
		return doa;
	}

	public void setDoa(String doa) {
		this.doa = doa;
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
