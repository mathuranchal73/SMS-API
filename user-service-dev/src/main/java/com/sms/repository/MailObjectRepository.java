package com.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.payload.MailObject;

@Repository
public interface MailObjectRepository extends JpaRepository<MailObject, Long> {


}
