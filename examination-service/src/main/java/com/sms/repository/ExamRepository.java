package com.sms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.model.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Long>{
	
	Optional<Exam> findById(Long examId);


}
