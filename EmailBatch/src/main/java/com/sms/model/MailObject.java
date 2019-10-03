package com.sms.model;

import java.net.URI;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sms.model.User;

@Entity
@Table(name = "mail_object",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "verificationLink"
            }),
            @UniqueConstraint(columnNames = {
                "email"
            }),
            @UniqueConstraint(columnNames = {
                    "user_uuid"
                })
    })
public class MailObject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String email;
	
	private URI verificationLink;
	
	private Date expiryDate;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;
    


	public MailObject(String name, String email, URI verificationLink, Date expiryDate, User user) {
		super();
		this.name = name;
		this.email = email;
		this.verificationLink = verificationLink;
		this.expiryDate = expiryDate;
		this.user = user;
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



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}

	

   
	

}
