package com.sms.payload;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.sms.model.Question;

public class AddQuestionRequest {
	
	private Question question;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	

   

}
