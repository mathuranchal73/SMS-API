package com.sms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
	
	Optional<Question> findById(Long questionId);

    List<Question> findByIdIn(List<Long> questionIds);

    List<Question> findByIdIn(List<Long> questionIds, Sort sort);

}
