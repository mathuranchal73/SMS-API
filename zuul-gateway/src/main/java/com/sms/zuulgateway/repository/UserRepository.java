package com.sms.zuulgateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.zuulgateway.bean.auth.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

	 User findByUsername(String username);
}
