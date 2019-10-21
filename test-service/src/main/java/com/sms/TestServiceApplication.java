package com.sms;

import java.util.Arrays;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.sms.web.filter.CorrelationHeaderFilter;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.sms")
@SpringBootApplication
public class TestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestServiceApplication.class, args);
	}
	
	
	 @Bean
	    public org.springframework.boot.web.servlet.FilterRegistrationBean correlationHeaderFilter() {
	        org.springframework.boot.web.servlet.FilterRegistrationBean filterRegBean = new org.springframework.boot.web.servlet.FilterRegistrationBean();
	        filterRegBean.setFilter(new CorrelationHeaderFilter());
	        filterRegBean.setUrlPatterns(Arrays.asList("/*"));

	        return filterRegBean;
	    }

}
