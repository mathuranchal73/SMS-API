package com.sms.payload;

import java.util.Date;

public class AttendanceRequest {
	
	private Date timeIn;
	
	private Long studentId;

	public Date getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(Date timeIn) {
		this.timeIn = timeIn;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	
	
}
