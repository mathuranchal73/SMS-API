package com.sms.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sms.model.ExamQuestionCount;
import com.sms.model.ExamQuestionMap;
import com.sms.model.Question;
import com.sms.model.QuestionPaper;

@Repository
public interface QuestionPaperRepository extends JpaRepository<QuestionPaper,Long>{
	
	Optional<QuestionPaper> findById(Long examId);

	boolean existsByQuestionId(Long id);
	boolean existsByExamId(Long examId);
	
	//@Query("SELECT NEW com.sms.model.ExamQuestionCount(v.question.id, count(v.id)) FROM Question v WHERE v.exam.id in :examIds GROUP BY v.question.id")
    //List<ExamQuestionCount> countByExamIdInGroupByExamId(@Param("examIds") List<Long> examIds);
	
	//@Query("SELECT NEW com.sms.model.ExamQuestionMap(q.exam_id,v),Question v FROM QuestionPaper q where q.exam_id = :examId  ")
	//@Query("SELECT q from QuestionPaper qp, Question q where qp.exam_id=:examId and q.id=qp.question_id")
	//List<Question> findQuestionByExamIdAndQuestionIdIn(@Param("examId") Long examId);
	
	@Query("SELECT question.id from QuestionPaper where exam.id=:examId")
	List<Long> findAllQuestionIdsByExamIdIn(@Param("examId") Long examId);
	
	@Transactional
	@Modifying
	@Query("DELETE from QuestionPaper where exam.id=:examId")
	void deleteByExamId(Long examId);
	
	@Transactional
	@Modifying
	@Query("DELETE from QuestionPaper where question.id=:questionId and exam.id=:examId")
	void deleteByExamIdAndQuestionId(Long questionId, Long examId);


	


}