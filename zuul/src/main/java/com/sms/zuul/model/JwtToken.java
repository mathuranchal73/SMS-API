package com.sms.zuul.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sms.zuul.model.audit.DateAudit;

@Entity
@Table(name = "jwttoken")
public class JwtToken extends DateAudit {
	
	 @Id
	 private String token;
	 
	 private Long userId;
	 
	 

	public JwtToken() {
		super();
	}

	public JwtToken(String token, Long userId) {
		super();
		this.token = token;
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	 
	 

}
