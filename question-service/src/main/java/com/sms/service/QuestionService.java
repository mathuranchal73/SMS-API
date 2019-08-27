package com.sms.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sms.model.Choice;
import com.sms.model.Question;
import com.sms.payload.QuestionRequest;
import com.sms.repository.QuestionRepository;


@Service
public class QuestionService {
	
	@Autowired
    private QuestionRepository questionRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
	
	public Question createQuestion(QuestionRequest questionRequest) {
        Question question = new Question();
        question.setQuestionText(questionRequest.getQuestionText());

        questionRequest.getChoices().forEach(choiceRequest -> {
            question.addChoice(new Choice(choiceRequest.getText(),choiceRequest.isCorrect(),choiceRequest.getScore()));
        });
        
        question.setAllowedTime(questionRequest.getAllowedTime());

        return questionRepository.save(question);
    }
	
	




}
