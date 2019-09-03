package com.sms.model;

import java.util.Collection;
import java.util.List;


public class ExamQuestionMap {
	
	private Exam exam;
    private List<Question> questionList;
	
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public List<Question> getQuestionList() {
		return questionList;
	}
	
	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
    

}
