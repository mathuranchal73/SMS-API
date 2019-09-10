package com.sms.zuul.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sms.zuul.model.User;
import com.sms.zuul.security.UserPrincipal;

@JsonDeserialize(as = UserPrincipal.class)
public class UserPrincipal implements UserDetails {
	
	
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

    private Collection<? extends GrantedAuthority> authorities;

 

    
	public UserPrincipal(Long id, String name, String email, String username, String password, Integer active,
			boolean isLocked, boolean isExpired, boolean isEnabled,  Collection<? extends GrantedAuthority> authorities) {
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
		this.authorities = authorities;
	}
	
	 public static UserPrincipal create(User user) {
	        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
	                new SimpleGrantedAuthority(role.getName().name())
	        ).collect(Collectors.toList());

	        return new UserPrincipal(
	                user.getId(),
	                user.getName(),
	                user.getEmail(),
	                user.getUsername(),
	                user.getPassword(),
	                user.getActive(),
	                user.isLocked(),
	                user.isExpired(),
	                user.isEnabled(),
	                authorities
	        );
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

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	 @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return authorities;
	    }


	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        UserPrincipal that = (UserPrincipal) o;
	        return Objects.equals(id, that.id);
	    }

	    @Override
	    public int hashCode() {

	        return Objects.hash(id);
	    }

}
