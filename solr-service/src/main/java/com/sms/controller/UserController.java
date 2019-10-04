package com.sms.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.document.User;
import com.sms.exception.ResourceNotFoundException;
import com.sms.repository.SolrUserRepository;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/v1/solr/user")
@Api(value="solr", description = "Solr Service", tags=("solr"))
public class UserController {
	
	 @Autowired
	 SolrUserRepository solrUserRepository;
	 
	 @GetMapping("/select/{name}")
	 @PreAuthorize("hasAnyRole('ADMIN','SYSTEM')")
	 public List<User> getUser(@PathVariable String name) {
	  return solrUserRepository.findByNameStartsWith(name);
	 }
	 
	 @PostMapping("/save")
	 @PreAuthorize("hasAnyRole('ADMIN','SYSTEM')")
	 public String saveAllUsers(@RequestBody List<User> userList) {
	       solrUserRepository.saveAll(userList);
	       return userList.size()+" Users saved!!!";
	 }
	 
	 @DeleteMapping("/deleteUser")
	 @PreAuthorize("hasAnyRole('ADMIN','SYSTEM')")
	 public String deleteUser(@RequestBody User user) {
	     try {
	      solrUserRepository.deleteById(user.getId());
	      return "User deleted succesfully!";
	      
	     }catch (Exception e){
	       return "Failed to delete user";
	     }
	 }
	 
	 @PutMapping("/update")
	 @PreAuthorize("hasAnyRole('ADMIN','SYSTEM')")
	 public String updateUser(@RequestBody User user) {
	     
	      User solrUser= solrUserRepository.findById(user.getId())
	    		  .orElseThrow(() -> new ResourceNotFoundException("User", "user id", user.getId()));
	      
	      solrUser.setName(user.getName());
	      
	      try {
	    	  solrUserRepository.save(solrUser);
	    	  return "User updated in Solr successfully";
	    	  
	     }catch (Exception e){
	       return "Exception encountered in updating user in Solr:"+e.getMessage();
	     }
	 }


}
