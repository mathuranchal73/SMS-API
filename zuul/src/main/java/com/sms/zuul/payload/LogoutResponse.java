package com.sms.zuul.payload;

public class LogoutResponse {
	
	Boolean success;
	String message;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LogoutResponse(Boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	

}
