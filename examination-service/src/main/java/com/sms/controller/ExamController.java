package com.sms.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.sms.model.Exam;
import com.sms.model.Question;
import com.sms.payload.ApiResponse;
import com.sms.payload.ExamRequest;
import com.sms.payload.QuestionRequest;
import com.sms.service.ExamService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/exams")
@Api(value="Exams", description = "Data service operations on Examination Service", tags=("exam-service"))
public class ExamController {
	
	@Autowired
	ExamService examService;
	
	
	protected Logger logger = LoggerFactory.getLogger(ExamController.class);
	

	@PostMapping
    @ApiOperation(value="Creates the Exam", notes="Creates a Exam",produces = "application/json", nickname="createExam")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody ExamRequest examRequest) {
        Exam exam = examService.createExam(examRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{examId}")
                .buildAndExpand(exam.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Exam Created Successfully"));
    }
	
  
	

}
