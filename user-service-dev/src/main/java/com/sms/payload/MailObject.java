package com.sms.payload;

import java.net.URI;
import java.util.Date;


public class MailObject {
	
	private String name;
	
	private String email;
	
	private URI verificationLink;
	
	private Date expiryDate;
	
	private String user_uuid;
    
	 public MailObject() {
	    	
	    }
	 
	 
	
	public MailObject(String name, String email, URI verificationLink, Date expiryDate, String user_uuid) {
		super();
		this.name = name;
		this.email = email;
		this.verificationLink = verificationLink;
		this.expiryDate = expiryDate;
		this.user_uuid = user_uuid;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public URI getVerificationLink() {
		return verificationLink;
	}

	public void setVerificationLink(URI verificationLink) {
		this.verificationLink = verificationLink;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getUser_uuid() {
		return user_uuid;
	}

	public void setUser_uuid(String user_uuid) {
		this.user_uuid = user_uuid;
	}

   
	

}
