package com.sms.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages = "com.sms")
@ComponentScan
public class SolrConfig {


	 @Value("${spring.solr.host}")
	 String solrURL;
	 
	 @Bean
	 public SolrClient solrClient() {
	  return new HttpSolrClient.Builder(solrURL).build();
	 }
	 
	 
	 @Bean
	 public SolrTemplate solrTemplate(SolrClient client) throws Exception {
	  return new SolrTemplate(client);
	 }
}
