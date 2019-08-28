package com.sms.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.sms.model.Choice;
import com.sms.model.Exam;
import com.sms.model.Question;
import com.sms.payload.ApiResponse;
import com.sms.payload.ChoiceRequest;
import com.sms.payload.ExamRequest;
import com.sms.payload.QuestionRequest;
import com.sms.payload.QuestionResponse;
import com.sms.repository.ExamRepository;
import com.sms.util.ModelMapper;

@Service
public class ExamService {

	@Autowired
	private ExamRepository examRepository;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ExamService.class);
	
	
	  
    @Autowired
    private EurekaClient eurekaClient;
   
    @Value("${service.question-service.serviceId}")
    private String questionServiceServiceId;
    
    @Autowired
	 private RestTemplate restTemplate;
	
    
    
	
	
	public Exam createExam(ExamRequest examRequest) {
		
		 Exam exam = new Exam();
		 

	        exam.setExamName(examRequest.getExamName());
	        examRequest.getQuestions().forEach(questionRequest->{exam.addQuestion(postQuestion(questionRequest));
	        });
	        
	        Instant now = Instant.now();
	        Instant expirationDateTime = now.plus(Duration.ofDays(examRequest.getExamDuration().getHours()))
	                .plus(Duration.ofHours(examRequest.getExamDuration().getMinutes()));

	        exam.setExpirationDateTime(expirationDateTime);
	        exam.setInstructions(examRequest.getInstructions());
	        exam.setTotalMarks(examRequest.getTotalMarks());
	        
	        return examRepository.save(exam);

	
	}
		
        
        private Question postQuestion(QuestionRequest questionRequest) {
        	//List<QuestionRequest> questRequestList = new ArrayList<>();
        	//questRequestList.add(questionRequest);
        	//MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
       	 	//bodyMap.add("questionRequest", ModelMapper.mapRequestToQuestionRequest(questionRequest));
	       // HttpHeaders headers = new HttpHeaders();
	        //headers.setContentType(MediaType.APPLICATION_JSON);
	       // HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
	        Application application = eurekaClient.getApplication(questionServiceServiceId);
			InstanceInfo instanceInfo = application.getInstances().get(0);
			String url = "http://"+instanceInfo.getIPAddr()+ ":"+instanceInfo.getPort()+"/"+"api/questions";
			QuestionResponse response= restTemplate.postForObject(url,questionRequest, QuestionResponse.class);
		    
		   return ModelMapper.mapQuestionResponseToQuestion(response);
	}


	
}
