package com.sms.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import com.sms.model.Question;
import com.sms.payload.QuestionResponse;
import com.sms.payload.ChoiceResponse;

public class ModelMapper {

	public static QuestionResponse mapQuestionToQuestionResponse(Question question) {
	
		QuestionResponse questionResponse= new QuestionResponse();
		questionResponse.setId(question.getId());
		questionResponse.setQuestionText(question.getQuestionText());
		
		 List<ChoiceResponse> choiceResponses = question.getChoices().stream().map(Choice -> {
			 ChoiceResponse choiceResponse = new ChoiceResponse();
	            choiceResponse.setId(Choice.getId());
	            choiceResponse.setText(Choice.getText());
	            choiceResponse.setCorrect(Choice.isCorrect());
	            choiceResponse.setScore(Choice.getScore());
	            return choiceResponse;
		 }).collect(Collectors.toList());
	            
		questionResponse.setChoices(choiceResponses);
		questionResponse.setAllowedTime(question.getAllowedTime());
		questionResponse.setScore(question.getScore());
		
		return questionResponse;
	}

}
