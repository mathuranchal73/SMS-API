package com.sms.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "results")
public class Result {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "exam_id", nullable = false)
	private Exam exam;
	
	private int Score;
	private int totalCorrectAns;
	private int timeTakenInMin;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public int getScore() {
		return Score;
	}
	public void setScore(int score) {
		Score = score;
	}
	
	public int getTotalCorrectAns() {
		return totalCorrectAns;
	}
	public void setTotalCorrectAns(int totalCorrectAns) {
		this.totalCorrectAns = totalCorrectAns;
	}
	public int getTimeTakenInMin() {
		return timeTakenInMin;
	}
	public void setTimeTakenInMin(int timeTakenInMin) {
		this.timeTakenInMin = timeTakenInMin;
	}
	
	
	

}
