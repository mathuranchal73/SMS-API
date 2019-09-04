package com.sms.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sms.model.Choice;
import com.sms.model.Question;
import com.sms.payload.ApiResponse;
import com.sms.payload.QuestionRequest;
import com.sms.payload.QuestionResponse;
import com.sms.util.ModelMapper;
import com.sms.exception.ResourceNotFoundException;
import com.sms.repository.QuestionRepository;


@Service
public class QuestionService {
	
	@Autowired
    private QuestionRepository questionRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
	
	public ResponseEntity<ApiResponse> createQuestion(QuestionRequest questionRequest) {
        Question question = new Question();

        question.setQuestionText(questionRequest.getQuestionText());
        questionRequest.getChoices().forEach(choiceRequest -> {
            question.addChoice(new Choice(choiceRequest.getText(),choiceRequest.isCorrect(),choiceRequest.getScore()));
        });
        
        question.setAllowedTime(questionRequest.getAllowedTime());

        questionRepository.save(question);
        return new ResponseEntity(new ApiResponse(true, "Question Added Successfully"),
		               HttpStatus.OK);
    }
	
	
	public QuestionResponse getQuestionByID(Long questionId) {
		
		 Question question = questionRepository.findById(questionId).orElseThrow(
	                () -> new ResourceNotFoundException("Question", "id", questionId));

		 return ModelMapper.mapQuestionToQuestionResponse(question);
	    }
	




}
