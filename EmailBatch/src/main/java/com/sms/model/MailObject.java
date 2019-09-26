package com.sms.model;

import java.net.URI;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class MailObject {
	
	@Id
	private Long id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("verificationLink")
	private URI verificationLink;
	
	@JsonProperty("expiryDate")
	private Date expiryDate;
	
	@JsonProperty("user_uuid")
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



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
