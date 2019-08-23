package com.sms.emailservice.service;

import org.springframework.mail.SimpleMailMessage;

import com.sms.emailservice.model.MailObject;

import freemarker.template.TemplateException;

public interface EmailService {
	
	 void sendSimpleMessage(String to,
             String subject,
             String text);
	 
	 void sendMessageWithAttachment(String to,
             String subject,
             String text,
             String pathToAttachment);

	void sendSimpleMessageUsingTemplate(MailObject mailObject) throws TemplateException;

}
