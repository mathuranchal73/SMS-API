package com.sms.zuulgateway;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.sms.zuulgateway.filters.pre.SimpleFilter;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@EntityScan(basePackageClasses = { 
		ZuulGatewayApplication.class,
		Jsr310JpaConverters.class 
})
public class ZuulGatewayApplication {
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(ZuulGatewayApplication.class, args);
	}
	
	 @Bean
	  public SimpleFilter simpleFilter() {
	    return new SimpleFilter();
	  }

}

