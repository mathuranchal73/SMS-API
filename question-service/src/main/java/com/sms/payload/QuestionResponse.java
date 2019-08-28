package com.sms.payload;
	
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

public class QuestionResponse {
	
	    private Long id;
	    private String questionText;
	    private List<ChoiceResponse> choices;
	    private int allowedTime;
	    private int score;
	    

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }


	    public List<ChoiceResponse> getChoices() {
	        return choices;
	    }

	    public void setChoices(List<ChoiceResponse> choices) {
	        this.choices = choices;
	    }

		public String getQuestionText() {
			return questionText;
		}

		public void setQuestionText(String questionText) {
			this.questionText = questionText;
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
