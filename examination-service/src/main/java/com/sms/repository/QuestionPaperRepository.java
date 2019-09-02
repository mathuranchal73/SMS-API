package com.sms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.model.QuestionPaper;

@Repository
public interface QuestionPaperRepository extends JpaRepository<QuestionPaper,Long>{
	
	Optional<QuestionPaper> findById(Long examId);

	boolean existsByQuestionId(Long id);
	


}