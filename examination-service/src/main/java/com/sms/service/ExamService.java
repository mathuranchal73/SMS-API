package com.sms.service;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.gson.Gson;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.sms.exception.ResourceNotFoundException;
import com.sms.model.Choice;
import com.sms.model.Exam;
import com.sms.model.ExamQuestionMap;
import com.sms.model.Question;
import com.sms.model.QuestionPaper;
import com.sms.model.Result;
import com.sms.model.User;
import com.sms.model.audit.UserDateAudit;
import com.sms.payload.AddQuestionRequest;
import com.sms.payload.AnswerRequest;
import com.sms.payload.ApiResponse;
import com.sms.payload.ChoiceRequest;
import com.sms.payload.ExamRequest;
import com.sms.payload.ExamResponse;
import com.sms.payload.QuestionRequest;
import com.sms.payload.QuestionResponse;
import com.sms.exception.BadRequestException;
import com.sms.repository.ExamRepository;
import com.sms.repository.QuestionPaperRepository;
import com.sms.repository.ResultRepository;
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
	
	@Autowired
	private ResultRepository resultRepository;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ExamService.class);
	
	
	  
    @Autowired
    private EurekaClient eurekaClient;
   
    @Value("${service.question-service.serviceId}")
    private String questionServiceServiceId;
    
    @Autowired
	 private RestTemplate restTemplate;
	
    
    
	
	
	public ResponseEntity<?> createExam(ExamRequest examRequest) {
		
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
	        
	        examRepository.save(exam);
	        
	        URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest().path("/{examId}")
	                .buildAndExpand(exam.getId()).toUri();

	        return ResponseEntity.created(location)
	                .body(new ApiResponse(true, "Exam Created Successfully"));

	
	}
	
	//public ExamResponse addQuestionAndGetUpdatedExam(Long examId, AddQuestionRequest addQuestionRequest, UserPrincipal currentUser) {
	public ResponseEntity<?> addQuestionAndGetUpdatedExam(String token,Long examId, AddQuestionRequest addQuestionRequest, UserPrincipal currentUser) {
		
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
				 return new ResponseEntity<>(new ExamResponse(null,false,"Sorry! You have already added the question in this exam"),HttpStatus.BAD_REQUEST);
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
					 temp.add(getQuestion(token,q));
				 }
				 
				 Collections.sort(temp);
				 eqm.setQuestionList(temp);
				 return new ResponseEntity<>(new ExamResponse(eqm,true,"Question Added Successfully and Exam updated"),HttpStatus.OK);
			 }
	            
	        } catch (Exception ex) {
	        	
	        	 return new ResponseEntity<>(new ExamResponse(null,false,"Exception Encountered in Adding Question to Exam"+ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);

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

		
	private Question getQuestion(String token,Long questionId) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", token);
			HttpEntity<Long> entity = new HttpEntity<Long>(questionId,headers);
		 Application application = eurekaClient.getApplication(questionServiceServiceId);
			InstanceInfo instanceInfo = application.getInstances().get(0);
			String url = "http://"+instanceInfo.getIPAddr()+ ":"+instanceInfo.getPort()+"/api/questions/"+questionId;
			  //String json=restTemplate.postForObject(url,questionId, String.class);
				//String json=restTemplate.getForObject(url, String.class); 
			//String json=restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			//String json=restTemplate.getForEntity(url, String.class, entity)
			 //ResponseEntity<Question> json = restTemplate.exchange(url,
		                //HttpMethod.GET, entity, Question.class);
			//String json=restTemplate.postForObject(url,entity,String.class);
			//Question response = new Gson().fromJson(json, Question.class);
			
			ResponseEntity<Question> json = restTemplate.exchange(url,
	                HttpMethod.GET, entity, Question.class);
			
			
			  
			  return json.getBody();
		
	}

		
	public ResponseEntity<?> getExam(String token,Long examId) {
		ExamQuestionMap examQuestionMap= new ExamQuestionMap();
		examQuestionMap.setExam(examRepository.findById(examId)
						.orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId)));
		
		 List<Long> questionIdList=questionPaperRepository.findAllQuestionIdsByExamIdIn(examId);
		 List<Question> temp= new ArrayList<>();
		 //temp.add(questionIdList.forEach(Question->{getQuestion(Question.getId())});));
		 for(Long q:questionIdList)
		 {
			 temp.add(this.getQuestion(token,q));
		 }
		 
		 examQuestionMap.setQuestionList(temp);
		 
		 return new ResponseEntity<>(new ExamResponse(examQuestionMap,true,"Found Exam Record"),HttpStatus.FOUND);
	 
		
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
	
	
	

	public ResponseEntity<?> removeQuestionAndGetUpdatedExam(String token,Long examId,Long questionId, UserPrincipal currentUser) {
		
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
								 temp.add(this.getQuestion(token,q));
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

	public ResponseEntity<?> submitAnswer(UserPrincipal currentUser, Long examId, @Valid AnswerRequest answerRequest) {
	
		try {
			if(userRepository.existsById(currentUser.getId()))
			{
				List<Choice> temp= answerRequest.getSelectedChoice();
				if(!temp.isEmpty())
				{
					int totalCorrectAnswers=0;
					int totalScore=0;
					Result result= new Result();
					for(Choice c:temp)
					{
						if(c.isCorrect())
						{
							totalCorrectAnswers=totalCorrectAnswers+1;
							totalScore=totalScore+c.getScore();
						}
					}
					result.setExam(examRepository.findById(examId)
							.orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId)));
					result.setUser(userRepository.findById(currentUser.getId())
							.orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId())));
					result.setScore(totalScore);
					result.setTotalCorrectAns(totalCorrectAnswers);
					resultRepository.save(result);
				}
				 return new ResponseEntity<>(new ApiResponse(false,"Empty Answer Request"),HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(new ApiResponse(false,"User is not Available"),HttpStatus.NOT_FOUND);
		
		}catch(Exception ex) {
			return new ResponseEntity<>(new ApiResponse(false,"Error occured in deletion of Question from Exam"),HttpStatus.INTERNAL_SERVER_ERROR);
			
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
