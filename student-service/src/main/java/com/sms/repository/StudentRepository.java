package com.sms.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sms.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
	

	 ArrayList<Student> findAll();
	 Optional<Student> findById(Long studentId);
	 Optional<Student> findByFirstName(String firstName);
	 Boolean existsByregistrationNo(String registrationNo);
	 Boolean existsBystudentEmail(String studentEmail);
	 @Query(getStudentCount)
	 Long getStudentCount();
	 
	 @Query(getStudentCountByEnabledId)
	 Long getTotalActiveStudentCount();
	 
	 @Query(getActiveStudentCountByAcademicSession)
	 Long getTotalActiveStudentCountByAcademicSession(String academicSession);
	 
	 @Query(getActiveStudentCountByDoa)
	 Long getTotalActiveStudentCountByDoa(String doa);
	 
	 
	 final String getStudentCount="SELECT COUNT(*) FROM Student";
	 final String getStudentCountByEnabledId= "SELECT COUNT(stu) FROM Student stu WHERE stu.enabled = 1";
	 final String getActiveStudentCountByAcademicSession="SELECT COUNT(stu) FROM Student stu where stu.enabled= 1 and stu.academicSessions=?1";
	 final String getActiveStudentCountByDoa="SELECT COUNT(stu) FROM Student stu where stu.enabled=1 and stu.doa=?1";

}
