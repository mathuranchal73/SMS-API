package com.sms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.model.AcademicSession;
import com.sms.model.Session;

@Repository
public interface AcademicSessionRepository extends JpaRepository<AcademicSession,Long> {
	
	 Optional<AcademicSession> findBySession(Session session);

}
