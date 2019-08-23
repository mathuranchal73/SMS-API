package com.sms.zuulgateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.zuulgateway.bean.auth.JwtToken;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, String> {

}
