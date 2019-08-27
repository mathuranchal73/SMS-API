package com.sms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.sms.model.audit.UserDateAudit;


@Entity
@Table(name = "questions")
public class Question {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 	private Long id;
		
		@NotBlank
	    @Size(max = 140)
	    private String questionText;
		
		@OneToMany(
	            mappedBy = "question",
	            cascade = CascadeType.ALL,
	            fetch = FetchType.EAGER,
	            orphanRemoval = true
	    )
	    @Size(min = 2, max = 6)
	    @Fetch(FetchMode.SELECT)
	    @BatchSize(size = 30)
	    private List<Choice> choices = new ArrayList<>();
		
		@ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "exam_id", nullable = false)
	    private Exam exam;
	    
		public Exam getExam() {
			return exam;
		}

		public void setExam(Exam exam) {
			this.exam = exam;
		}

		@NotNull
	    private int allowedTime;
	    
		@NotNull
	    private int score=0;

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
		
		public void addChoice(Choice choice) {
	        choices.add(choice);
	        this.setScore(this.score+choice.getScore());
	        choice.setQuestion(this);
	    }

	    public void removeChoice(Choice choice) {
	        choices.remove(choice);
	        choice.setQuestion(null);
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
