package com.sms.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



public class Question {
	
    private Long id;
	
    private String questionText;
	
    private List<Choice> choices = new ArrayList<>();
    
    private int allowedTime;
    
    private int score;
    
    

	

	public Question(Long id, String questionText, List<com.sms.model.Choice> choices, int allowedTime, int score) {
		super();
		this.id = id;
		this.questionText = questionText;
		this.choices = choices;
		this.allowedTime = allowedTime;
		this.score=score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
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
