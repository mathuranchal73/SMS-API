package com.sms.payload;

public class StudentIdentityAvailability {
	
	private Boolean available;

    public StudentIdentityAvailability(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

}
