package com.sms.util;

import java.util.List;
import java.util.stream.Collectors;


import com.sms.model.Choice;
import com.sms.model.Question;
import com.sms.payload.QuestionRequest;
import com.sms.payload.QuestionResponse;

public class ModelMapper {
	
	public static Question mapQuestionResponseToQuestion(QuestionResponse questionResponse) {
		
		Question question= new Question();
		question.setId(questionResponse.getId());
		question.setQuestionText(questionResponse.getQuestionText());
		
		
		 List<Choice> choices= questionResponse.getChoices().stream().map(choiceResponse -> {
			 Choice choice = new Choice();
	            choice.setId(choiceResponse.getId());
	           choice.setText(choiceResponse.getText());
	            choice.setCorrect(choiceResponse.isCorrect());
	            choice.setScore(choiceResponse.getScore());
	            return choice;
		 }).collect(Collectors.toList());
	            
		question.setChoices(choices);
		question.setAllowedTime(questionResponse.getAllowedTime());
		question.setScore(questionResponse.getScore());
		
		return question;
	}

	public static QuestionRequest mapRequestToQuestionRequest(QuestionRequest questionRequest) {
		
		QuestionRequest qr= new QuestionRequest();
		qr.setQuestionText(questionRequest.getQuestionText());
		qr.setChoices(questionRequest.getChoices());
		qr.setScore(questionRequest.getAllowedTime());
		qr.setScore(questionRequest.getScore());
		// TODO Auto-generated method stub
		return qr;
	}


}
