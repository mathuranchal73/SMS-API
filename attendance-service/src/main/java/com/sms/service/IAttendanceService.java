package com.sms.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.sms.payload.AttendanceRequest;
import com.sms.security.UserPrincipal;

public interface IAttendanceService {


	ResponseEntity<?> markStudentPresent(String token, UserPrincipal currentUser,
			@Valid AttendanceRequest attendanceRequest);

}
