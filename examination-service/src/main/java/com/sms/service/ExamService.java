package com.sms.service;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.model.Choice;
import com.sms.model.Exam;
import com.sms.model.Question;
import com.sms.payload.ExamRequest;
import com.sms.payload.QuestionRequest;
import com.sms.repository.ExamRepository;

@Service
public class ExamService {

	@Autowired
	private ExamRepository questionRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ExamService.class);
	
	
	public Exam createExam(ExamRequest examRequest) {
        Exam exam = new Exam();
        exam.setExamName(examRequest.getExamName());
        
        Instant now = Instant.now();
        Instant expirationDateTime = now.plus(Duration.ofDays(examRequest.getExamDuration().getHours()))
                .plus(Duration.ofHours(examRequest.getExamDuration().getMinutes()));

        exam.setExpirationDateTime(expirationDateTime);

        examRequest.getQuestions().forEach(questionRequest -> {
            exam.addQuestion(new Choice(choiceRequest.getText(),choiceRequest.isCorrect(),choiceRequest.getScore()));
        });
        
        question.setAllowedTime(questionRequest.getAllowedTime());

        return questionRepository.save(question);
    }
	
}
