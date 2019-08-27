package com.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.sms.service.ExamService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/exams")
@Api(value="Exams", description = "Data service operations on Examination Service", tags=("exam-service"))
public class ExamController {
	
	@Autowired
	ExamService examService;
	
	

}
