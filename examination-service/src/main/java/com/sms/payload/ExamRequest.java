package com.sms.payload;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class ExamRequest {

	@NotBlank
    @Size(max = 140)
	private String examName;
	
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
