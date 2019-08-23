package com.sms.emailservice.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.sms.emailservice.model.MailObject;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Component
public class EmailServiceImpl implements EmailService {
	
	 @Autowired
	 public JavaMailSender emailSender;
	 
	 @Autowired
	 private Configuration freemarkerConfig;

	@Override
	public void sendSimpleMessage(String to, String subject, String text) {
		try {
			System.out.println("Inside SendSimpleMessage");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("mathuranchal90@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

           // emailSender.send(message);
            
            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
		
	}

	public void sendSimpleMessageUsingTemplate(MailObject mailObject)
	{
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		 Map<String, Object> model = new HashMap();
	        model.put("user", mailObject.getName());
	        model.put("verificationLink", mailObject.getVerificationLink());
	        model.put("Expiration Time", mailObject.getExpiryDate());
	        
		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
		
			try {
				Template t = freemarkerConfig.getTemplate("welcome.ftl");
				String body = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			
				helper.setTo(mailObject.getEmail());
				helper.setText(body, true); // set to html
		        helper.setSubject("Test Mail");
		        emailSender.send(message);

			} catch (TemplateNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedTemplateNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		
		
	}

	@Override
	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
		try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		
	}
	

}
