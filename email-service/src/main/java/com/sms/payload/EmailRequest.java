package com.sms.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EmailRequest {

	@NotBlank
    @Size(max = 40)
    @Email
    private String to_email;
	
	@NotBlank
	@Size(max=60)
	private String subject;
	
	@Size(max=20)
	private String display_name;
	
	@NotBlank
	@Size(max=40)
	private String reply_to;
	
	private String cuid;
	
	@NotBlank
	private String email_name;
	
	@Size(max = 40)
    @Email
	private String cc_email;
	
	@Size(max= 40)
	@Email
	private String bcc_email;
	
	@Size(max=40)
	@Email
	private String from_email;
	
	
	private String body;


	public String getTo_email() {
		return to_email;
	}


	public void setTo_email(String to_email) {
		this.to_email = to_email;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getDisplay_name() {
		return display_name;
	}


	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}


	public String getReply_to() {
		return reply_to;
	}


	public void setReply_to(String reply_to) {
		this.reply_to = reply_to;
	}


	public String getCuid() {
		return cuid;
	}


	public void setCuid(String cuid) {
		this.cuid = cuid;
	}


	public String getEmail_name() {
		return email_name;
	}


	public void setEmail_name(String email_name) {
		this.email_name = email_name;
	}


	public String getCc_email() {
		return cc_email;
	}


	public void setCc_email(String cc_email) {
		this.cc_email = cc_email;
	}


	public String getBcc_email() {
		return bcc_email;
	}


	public void setBcc_email(String bcc_email) {
		this.bcc_email = bcc_email;
	}


	public String getFrom_email() {
		return from_email;
	}


	public void setFrom_email(String from_email) {
		this.from_email = from_email;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}
	
	
	
}
