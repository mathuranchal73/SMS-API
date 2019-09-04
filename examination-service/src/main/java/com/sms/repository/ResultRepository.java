package com.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.model.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result,Long> {

}
