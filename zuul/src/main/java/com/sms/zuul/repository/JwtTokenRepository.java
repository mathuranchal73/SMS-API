package com.sms.zuul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.zuul.model.JwtToken;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, String> {

}
