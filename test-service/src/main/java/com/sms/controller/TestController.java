package com.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.service.ExternalNewsService;
import com.sms.service.NewsService;



@RestController
@RequestMapping("/v1/test")
public class TestController {
	
	
	 	@Autowired
	    private ExternalNewsService externalNewsService;

	    @Autowired
	    private NewsService newsService;


	    @RequestMapping("externalNews")
	    public String externalNews() {
	        return externalNewsService.getNews();
	    }


	    @RequestMapping("news")
	    public String news() {
	        return newsService.getNews();
	    }
	

}
