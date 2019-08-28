package com.sms.payload;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sms.model.Question;

public class ExamRequest {

	@NotBlank
    @Size(max = 140)
	private String examName;
	
	
	@NotNull
    @Size(min = 2, max = 20)
    @Valid
	private List<QuestionRequest> questions = new ArrayList<>();
	
	private String instructions;
	
	 @NotNull
	 @Valid
	 private ExamDuration examDuration;
	 
	  private int totalMarks=0;

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}


	public List<QuestionRequest> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionRequest> questions) {
		this.questions = questions;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

    public ExamDuration getExamDuration() {
		return examDuration;
	}

	public void setExamDuration(ExamDuration examDuration) {
        this.examDuration = examDuration;
    }
	
	public int getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}
}
