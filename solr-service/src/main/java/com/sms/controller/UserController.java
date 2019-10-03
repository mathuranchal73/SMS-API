package com.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.document.User;
import com.sms.repository.SolrUserRepository;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/v1/solr/user")
@Api(value="solr", description = "Solr Service", tags=("solr"))
public class UserController {
	
	 @Autowired
	 SolrUserRepository solrUserRepository;
	 
	 @GetMapping("/{name}")
	 public List<User> getUser(@PathVariable String name) {
	  return solrUserRepository.findByNameStartsWith(name);
	 }

}
