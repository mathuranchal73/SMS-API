package com.sms.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.sms.exception.ResourceNotFoundException;
import com.sms.model.Exam;
import com.sms.model.ExamQuestionMap;
import com.sms.model.Question;
import com.sms.model.QuestionPaper;
import com.sms.model.User;
import com.sms.model.audit.UserDateAudit;
import com.sms.payload.AddQuestionRequest;
import com.sms.payload.ApiResponse;
import com.sms.payload.ChoiceRequest;
import com.sms.payload.ExamRequest;
import com.sms.payload.ExamResponse;
import com.sms.payload.QuestionRequest;
import com.sms.payload.QuestionResponse;
import com.sms.exception.BadRequestException;
import com.sms.repository.ExamRepository;
import com.sms.repository.QuestionPaperRepository;
import com.sms.repository.UserRepository;
import com.sms.security.UserPrincipal;
import com.sms.util.ModelMapper;



@Service
public class ExamService extends UserDateAudit {

	@Autowired
	private ExamRepository examRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionPaperRepository questionPaperRepository;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ExamService.class);
	
	
	  
    @Autowired
    private EurekaClient eurekaClient;
   
    @Value("${service.zuul-gateway.serviceId}")
    private String zuulGatewayServiceId;
    
    @Autowired
	 private RestTemplate restTemplate;
	
    
    
	
	
	public Exam createExam(ExamRequest examRequest) {
		
		 Exam exam = new Exam();
		 

	        exam.setExamName(examRequest.getExamName());
	        examRequest.setTotalMarks(examRequest.getTotalMarks());
	        
	       // examRequest.getQuestions().forEach(questionRequest->{exam.addQuestion(postQuestion(questionRequest));
	        //});
	        
	        Instant now = Instant.now();
	        Instant expirationDateTime = now.plus(Duration.ofDays(examRequest.getExamDuration().getHours()))
	                .plus(Duration.ofHours(examRequest.getExamDuration().getMinutes()));

	        exam.setExpirationDateTime(expirationDateTime);
	        exam.setInstructions(examRequest.getInstructions());
	        exam.setTotalMarks(examRequest.getTotalMarks());
	        
	        return examRepository.save(exam);

	
	}
	
	//public ExamResponse addQuestionAndGetUpdatedExam(Long examId, AddQuestionRequest addQuestionRequest, UserPrincipal currentUser) {
	public ExamQuestionMap addQuestionAndGetUpdatedExam(Long examId, AddQuestionRequest addQuestionRequest, UserPrincipal currentUser) {
		
		Exam exam= examRepository.findById(examId)
				.orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));
		
		//User user= userRepository.getOne(currentUser.getId());
		
		//List<Long> selectedQuestionId=selectedQuestionId.addAll(addQuestionRequest.getQuestionId().stream().forEach({questionId->selectedQuestionId}););
		
		QuestionPaper questionPaper= new QuestionPaper();
		questionPaper.setExam(exam);
		questionPaper.setQuestion(addQuestionRequest.getQuestion());
		 try {
			 if(questionPaperRepository.existsByQuestionId(questionPaper.getQuestion().getId()))
			 {
				 return null;
				// return new ResponseEntity(new ApiResponse(false, "Sorry! You have already added the question in this exam"),
		          //       HttpStatus.BAD_REQUEST);
			 }
			 else
			 {
				 QuestionPaper qp=questionPaperRepository.save(questionPaper);
				 ExamQuestionMap eqm= new ExamQuestionMap();
				 eqm.setExam(examRepository.findById(qp.getExam().getId())
						 .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", qp.getExam().getId())));
				 List<Long> questionIdList=questionPaperRepository.findAllQuestionIdsByExamIdIn(qp.getExam().getId());
				 List<Question> temp= new ArrayList<>();
				 //temp.add(questionIdList.forEach(Question->{getQuestion(Question.getId())});));
				 for(Long q:questionIdList)
				 {
					 temp.add(getQuestion(q));
				 }
				 
				 Collections.sort(temp);
				 eqm.setQuestionList(temp);
					  return eqm;
			 }
	            
	        } catch (Exception ex) {
	        	
	        	System.out.println(ex.getMessage());
	           // logger.info("QuestionPaper {} has already been added in Exam {}", currentUser.getId(), examId);
	        	// return new ResponseEntity(null,
		          //       HttpStatus.INTERNAL_SERVER_ERROR);
	        	
	        	return null;
	        }
		 
		 

		 /**
	        //-- Question Saved, Return the updated Exam Response now --

	        // Retrieve Vote Counts of every choice belonging to the current poll
	        List<ExamQuestionCount> question = questionPaperRepository.countByQuestionIdGroupByExamId(examId);

	        Map<Long, Long> questionsExamMap = questionPaper.stream()
	                .collect(Collectors.toMap(ExamQuestionCount::getExamId, ExamQuestionCount::getQuestionCount));

	        // Retrieve poll creator details
	        User creator = userRepository.findById(poll.getCreatedBy())
	                .orElseThrow(() -> new ResourceNotFoundException("User", "id", poll.getCreatedBy()));

	        return ModelMapper.mapPollToPollResponse(poll, choiceVotesMap, creator, vote.getChoice().getId());  **/
	}

		
	private Question getQuestion(Long questionId) {
		 Application application = eurekaClient.getApplication(zuulGatewayServiceId);
			InstanceInfo instanceInfo = application.getInstances().get(0);
			String url = "http://"+instanceInfo.getIPAddr()+ ":"+instanceInfo.getPort()+"/question-api/"+"api/questions/"+questionId;
			  //String json=restTemplate.postForObject(url,questionId, String.class);
				String json=restTemplate.getForObject(url, String.class); 
			Question response = new Gson().fromJson(json, Question.class);
			  
			  return response;
		
	}

		
	public ExamQuestionMap getExam(Long examId) {
		ExamQuestionMap examQuestionMap= new ExamQuestionMap();
		examQuestionMap.setExam(examRepository.findById(examId)
						.orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId)));
		
		 List<Long> questionIdList=questionPaperRepository.findAllQuestionIdsByExamIdIn(examId);
		 List<Question> temp= new ArrayList<>();
		 //temp.add(questionIdList.forEach(Question->{getQuestion(Question.getId())});));
		 for(Long q:questionIdList)
		 {
			 temp.add(this.getQuestion(q));
		 }
		 
		 examQuestionMap.setQuestionList(temp);
			  return examQuestionMap;
	 
		
	}

	public ResponseEntity<?> deleteExamById(Long examId) {
		if(examRepository.existsById(examId))
		{
			try {
				questionPaperRepository.deleteByExamId(examId);
				examRepository.deleteById(examId);
				return new ResponseEntity(new ApiResponse(true, "Exam Record Deleted Successfully!"),
		                 HttpStatus.OK);
			} catch (Exception e) {
				logger.error(e.getMessage());
				return new ResponseEntity(new ApiResponse(false, "Exception Encountered in deleting Student"),
		                 HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
		return new ResponseEntity<>(new ApiResponse(false,"Exam not found with ID"+examId),HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> removeQuestionAndGetUpdatedExam(Long examId,Long questionId, UserPrincipal currentUser) {
		
		 try {
			 if(questionPaperRepository.existsByExamId(examId))
			 {
				 if(questionPaperRepository.existsByQuestionId(questionId))
				 	{
					 questionPaperRepository.deleteByExamIdAndQuestionId(questionId, examId);

					 	ExamQuestionMap examQuestionMap= new ExamQuestionMap();
						examQuestionMap.setExam(examRepository.findById(examId)
										.orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId)));
						
						 List<Long> questionIdList=questionPaperRepository.findAllQuestionIdsByExamIdIn(examId);
						 List<Question> temp= new ArrayList<>();
						 //temp.add(questionIdList.forEach(Question->{getQuestion(Question.getId())});));
							 for(Long q:questionIdList)
							 {
								 temp.add(this.getQuestion(q));
							 }
							 
							 examQuestionMap.setQuestionList(temp);
						 
							 return new ResponseEntity<>(new ExamResponse(examQuestionMap,true,"Question Deleted Successfully"),HttpStatus.OK);
				 	}
					
				 return new ResponseEntity<>(new ApiResponse(false,"Question not found with ID :"+questionId+"in Exam :"+examId),HttpStatus.NOT_FOUND);
				 	}
				 
			  return new ResponseEntity<>(new ApiResponse(false,"Exam not found with ID"+examId),HttpStatus.NOT_FOUND);
			 }
		 catch(Exception e)
		 {
	        	logger.error("Error occured in deletion of Question from Exam");
			 return new ResponseEntity<>(new ApiResponse(false,"Error occured in deletion of Question from Exam"+examId+ e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	        }
			
	}	

		
        
       /** private Question postQuestion(QuestionRequest questionRequest) {
        	//List<QuestionRequest> questRequestList = new ArrayList<>();
        	//questRequestList.add(questionRequest);
        	//MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
       	 	//bodyMap.add("questionRequest", ModelMapper.mapRequestToQuestionRequest(questionRequest));
	       // HttpHeaders headers = new HttpHeaders();
	        //headers.setContentType(MediaType.APPLICATION_JSON);
	       // HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
	        Application application = eurekaClient.getApplication(zuulGatewayServiceId);
			InstanceInfo instanceInfo = application.getInstances().get(0);
			String url = "http://"+instanceInfo.getIPAddr()+ ":"+instanceInfo.getPort()+"/question-api/"+"api/questions";
			  String json=restTemplate.postForObject(url,questionRequest, String.class);
			  Question response = new Gson().fromJson(json, Question.class);
			
		    
		   return response;
	}**/


	
}
