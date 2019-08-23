package com.sms.userservice.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sms.userservice.model.audit.DateAudit;

@Entity
@Table(name = "userprofile", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
            })            
    })
	public class UserProfile extends DateAudit {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
		@NotBlank
	    @Size(max = 15)
	    private String username;
		
	    @NotBlank
	    @Size(max = 40)
	    private String name;
	    
	    

	    public UserProfile(Long id, String username, String name) {
	        this.id = id;
	        this.username = username;
	        this.name = name;
	        
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	    

}
