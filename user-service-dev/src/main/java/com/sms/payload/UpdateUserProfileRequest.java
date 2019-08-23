package com.sms.payload;

import javax.validation.constraints.Size;

public class UpdateUserProfileRequest {

	
	@Size(max = 200)
	private String displayPic;
	
	@Size(max = 100)
	private String address;
	@Size(max = 20)
    private String city;
    @Size(max = 10)
    private String phoneNo;
    
    
	public String getDisplayPic() {
		return displayPic;
	}
	public void setDisplayPic(String displayPic) {
		this.displayPic = displayPic;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
    
    
    
    
}
