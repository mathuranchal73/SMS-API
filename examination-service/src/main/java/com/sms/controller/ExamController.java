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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.sms.model.ExamQuestionMap;
import com.sms.payload.ApiResponse;
import com.sms.payload.ExamRequest;
import com.sms.payload.QuestionRequest;
import com.sms.payload.ExamResponse;
import com.sms.payload.AddQuestionRequest;
import com.sms.payload.AnswerRequest;
import com.sms.security.CurrentUser;
import com.sms.security.UserPrincipal;
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
	

	@PostMapping("/createExam")
    @ApiOperation(value="Creates the Exam", notes="Creates a Exam",produces = "application/json", nickname="createExam")
    public ResponseEntity<?> createExam(@Valid @RequestBody ExamRequest examRequest) {
        
		return examService.createExam(examRequest);    
    }
	
	
	@PostMapping("/{examId}/addQuestions")
    @ApiOperation(value="Adds the questions for the provided Exam Id and current User", notes="Adds the questions for the provided Exam Id and current User",produces = "application/json", nickname="addQuestions")
    public ResponseEntity<?> addQuestion(@CurrentUser UserPrincipal currentUser,
                         @PathVariable Long examId,
                         @Valid @RequestBody AddQuestionRequest addQuestionRequest) {
         return examService.addQuestionAndGetUpdatedExam(examId, addQuestionRequest, currentUser);
    }
	
	@DeleteMapping("/{examId}/removeQuestion/{questionId}")
    @ApiOperation(value="Adds the questions for the provided Exam Id and current User", notes="Adds the questions for the provided Exam Id and current User",produces = "application/json", nickname="addQuestions")
    public ResponseEntity<?> removeQuestionAndGetUpdatedExam(@CurrentUser UserPrincipal currentUser,@PathVariable Long examId,
                         @PathVariable Long questionId) {
         return examService.removeQuestionAndGetUpdatedExam(examId,questionId,currentUser);
    }
	
	@GetMapping("/{examId}")
	@ApiOperation(value="Gets the Exam details and questions for the provided Exam Id", notes="Gets teh Exam details and questions for the provided Exam id", produces="application/json", nickname="getExam")
	public ResponseEntity<?> getExam(@CurrentUser UserPrincipal currentUser, @PathVariable Long examId) {
		return examService.getExam(examId);
	}
	

    @PostMapping("/{examId}/submitAnswer")
    @ApiOperation(value="Submits the Answer Model for the provided Exam Id and current User", notes="Submits the Answer Model for the provided Exam Id and current User",produces = "application/json", nickname="submitAnswer")
    public ResponseEntity<?> submitAnswer(@CurrentUser UserPrincipal currentUser,
                         @PathVariable Long examId,
                         @Valid @RequestBody AnswerRequest answerRequest) {
    	
    	return examService.submitAnswer(currentUser,examId,answerRequest);
        
    }
	
	 @DeleteMapping("/{examId}")
	 @ApiOperation(value="Delete", notes="Delete the Exam Record", nickname="deleteExam")
	 public ResponseEntity<?> deleteExamById(@PathVariable Long examId) {
		 	return examService.deleteExamById(examId);
	 	}
	 
	
	

}
