package com.sms.payload;

import java.time.Instant;
import java.util.List;

import com.sms.model.Exam;
import com.sms.model.ExamQuestionMap;
import com.sms.model.Question;
import com.sms.payload.UserSummary;

public class ExamResponse {
	
	private ExamQuestionMap examQuestionMap;
    
    private Boolean success;
    private String message;
    
    
    
    
	
	public ExamResponse(ExamQuestionMap examQuestionMap, Boolean success, String message) {
		super();
		this.examQuestionMap = examQuestionMap;
		this.success = success;
		this.message = message;
	}
	public ExamQuestionMap getExamQuestionMap() {
		return examQuestionMap;
	}
	public void setExamQuestionMap(ExamQuestionMap examQuestionMap) {
		this.examQuestionMap = examQuestionMap;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
    

}
