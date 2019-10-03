package com.sms.repository;

import java.util.List;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.sms.document.User;

public interface SolrUserRepository extends SolrCrudRepository<User, String>{
	
	List<User> findByNameStartsWith(String name);

}
