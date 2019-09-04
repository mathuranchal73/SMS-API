package com.sms.payload;

import java.util.List;

import com.sms.model.Choice;
import com.sms.model.User;

public class AnswerRequest {
	
	private Long examId;
	
	private List<Choice> selectedChoice;
	

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public List<Choice> getSelectedChoice() {
		return selectedChoice;
	}

	public void setSelectedChoice(List<Choice> selectedChoice) {
		this.selectedChoice = selectedChoice;
	}

	

		

}
