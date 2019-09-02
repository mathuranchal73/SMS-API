package com.sms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sms.model.audit.UserDateAudit;



@Entity
@Table(name = "question_paper")
public class QuestionPaper extends UserDateAudit {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	 	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "exam_id", nullable = false)
	    private Exam exam;
	 	
	 	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "question_id", nullable = false)
	 	private Question question;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Exam getExam() {
			return exam;
		}

		public void setExam(Exam exam) {
			this.exam = exam;
		}

		public Question getQuestion() {
			return question;
		}

		public void setQuestion(Question question) {
			this.question = question;
		}

		

		
	 	
}
