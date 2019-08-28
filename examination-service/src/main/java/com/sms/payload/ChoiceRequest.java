package com.sms.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChoiceRequest {
	
	@NotBlank
    @Size(max = 40)
    private String text;
	
	private boolean correct;
	
    private int score;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	

    
}
