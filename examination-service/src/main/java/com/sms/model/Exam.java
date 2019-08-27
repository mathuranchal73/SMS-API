package com.sms.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="exams")
public class Exam {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @NotBlank
	    @Size(max = 140)
	    private String examName;
	    
	    @NotNull
	    private Instant expirationDateTime;
	    
	    @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(name = "exam_questions",
	            joinColumns = @JoinColumn(name = "exam_id"),
	            inverseJoinColumns = @JoinColumn(name = "question_id"))
	    private List<Question> questions = new ArrayList<>();
	    
	    private String instructions;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getExamName() {
			return examName;
		}

		public void setExamName(String examName) {
			this.examName = examName;
		}

		public Instant getExpirationDateTime() {
			return expirationDateTime;
		}

		public void setExpirationDateTime(Instant expirationDateTime) {
			this.expirationDateTime = expirationDateTime;
		}

		public List<Question> getQuestions() {
			return questions;
		}

		public void setQuestions(List<Question> questions) {
			this.questions = questions;
		}

		public String getInstructions() {
			return instructions;
		}

		public void setInstructions(String instructions) {
			this.instructions = instructions;
		}
	    
	    

}
