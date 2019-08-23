package com.sms.zuulgateway.bean.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = DbUserDetails.class)
public class DbUserDetails implements UserDetails{
	
		private Long id;
	
	    private String name;
	
	    @JsonIgnore
	    private String email;

	    private String username;
	    @JsonIgnore
	    private String password;
	    private Integer active;
	    private boolean isLocked;
	    private boolean isExpired;
	    private boolean isEnabled;
	    private List<GrantedAuthority> authorities;
	    
	    
	    
	    
		public DbUserDetails(Long id, String name, String email, String username, String password, Integer active,
				boolean isLocked, boolean isExpired, boolean isEnabled, String[] authorities) {
			super();
			this.id = id;
			this.name = name;
			this.email = email;
			this.username = username;
			this.password = password;
			this.active = active;
			this.isLocked = isLocked;
			this.isExpired = isExpired;
			this.isEnabled = isEnabled;
			this.authorities = AuthorityUtils.createAuthorityList(authorities);
		}

		
		
		 public DbUserDetails(String username,  String [] authorities) {
		        this.username = username;
		        this.authorities = AuthorityUtils.createAuthorityList(authorities);
		    }

		public DbUserDetails() {
			super();
		}


		public String getUsername() {
			return username;
		}



		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}


		public boolean isLocked() {
			return isLocked;
		}


		public boolean isExpired() {
			return isExpired;
		}

		public boolean isEnabled() {
			return isEnabled;
		}


		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}


		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return active==1;
		}

		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return !isLocked;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return !isExpired;
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
		}
	    
		 
	    
		
	    
	    

}
