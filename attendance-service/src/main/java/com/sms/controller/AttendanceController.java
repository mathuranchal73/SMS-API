package com.sms.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.payload.AttendanceRequest;
import com.sms.security.CurrentUser;
import com.sms.security.UserPrincipal;
import com.sms.service.IAttendanceService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/v1/attendance")
@Api(value="attendance", description = "Data service operations for Attendance", tags=("attendance"))
public class AttendanceController {

	private static Logger logger= LoggerFactory.getLogger("AttendanceController.class");
	
	@Autowired
	IAttendanceService attendanceService;
	
	@PostMapping("/student/markPresent")
	 @PreAuthorize("hasRole('TEACHER')")
	 public ResponseEntity<?> markStudentPresent(@RequestHeader("Authorization") String token,@CurrentUser UserPrincipal currentUser,
             @Valid @RequestBody AttendanceRequest attendanceRequest){
		
		return attendanceService.markStudentPresent(token,currentUser,attendanceRequest);
		
	}

}
