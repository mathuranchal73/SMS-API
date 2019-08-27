package com.sms.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sms.model.Question;
import com.sms.payload.ApiResponse;
import com.sms.payload.QuestionRequest;
import com.sms.repository.QuestionRepository;
import com.sms.service.QuestionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/questions")
@Api(value="questions", description = "Data service operations on Questions Service", tags=("question-service"))
public class QuestionController {
	
	@Autowired
    private QuestionRepository questionRepository;
	
	@Autowired
    private QuestionService questionService;
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	
	@PostMapping
    @ApiOperation(value="Creates the Question", notes="Creates a Question",produces = "application/json", nickname="createQuestion")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionRequest questionRequest) {
        Question question = questionService.createQuestion(questionRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{questionId}")
                .buildAndExpand(question.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Question Created Successfully"));
    }

	
}
