package com.sms.payload;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class QuestionRequest {
	
	@NotBlank
    @Size(max = 140)
    private String questionText;
	
	@NotNull
    @Size(min = 2, max = 6)
    @Valid
    private List<ChoiceRequest> choices;
	
	@NotNull
    private int allowedTime;
	
	@NotNull
    private int score;

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public List<ChoiceRequest> getChoices() {
		return choices;
	}

	public void setChoices(List<ChoiceRequest> choices) {
		this.choices = choices;
	}

	public int getAllowedTime() {
		return allowedTime;
	}

	public void setAllowedTime(int allowedTime) {
		this.allowedTime = allowedTime;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	

}
