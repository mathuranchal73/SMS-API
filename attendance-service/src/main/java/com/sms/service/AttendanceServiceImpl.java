package com.sms.service;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.sms.document.Attendance;
import com.sms.event.MarkAttendanceEvent;
import com.sms.exception.ResourceNotFoundException;
import com.sms.kafka.Producer;
import com.sms.model.Student;
import com.sms.model.User;
import com.sms.payload.ApiResponse;
import com.sms.payload.AttendanceRequest;
import com.sms.repository.AttendanceRepository;
import com.sms.repository.UserRepository;
import com.sms.security.UserPrincipal;

@Service
public class AttendanceServiceImpl implements IAttendanceService {
	
	private static final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);
	
	
	  
    @Autowired
    private EurekaClient eurekaClient;
   
    @Value("${service.student-service.serviceId}")
    private String studentServiceServiceId;
    
	@Value("${tbd.date.pattern")
	private static String pattern;
    
    @Autowired
	 private RestTemplate restTemplate;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    AttendanceRepository attendanceRepository;
    
    @Autowired
    Producer producer;
    
    @Autowired
	private ApplicationEventPublisher eventPublisher;

	@Override
	public ResponseEntity<?> markStudentPresent(String token, UserPrincipal currentUser,@Valid AttendanceRequest attendanceRequest) {
		User user= userRepository.findById(currentUser.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));
		Student student= getStudentbyID(token,attendanceRequest.getStudentId());
		Attendance attendance= new Attendance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		attendance.setStudent(student);
		attendance.setDateOfAttendance(simpleDateFormat.format(attendanceRequest.getTimeIn()));
		attendance.setTimeIn(attendanceRequest.getTimeIn().toInstant());
		try {
			attendanceRepository.save(attendance);
			logger.debug("Attendance saved to Mongo");
			eventPublisher.publishEvent(new MarkAttendanceEvent(user,attendance));
			return new ResponseEntity<>(new ApiResponse(true,"Attendance Marked Successfully"),HttpStatus.CREATED);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	private synchronized Student getStudentbyID(String token,Long studentId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", token);
		HttpEntity<Long> entity = new HttpEntity<Long>(studentId,headers);
		Application application = eurekaClient.getApplication(studentServiceServiceId);
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+ ":"+instanceInfo.getPort()+"/v1/student/"+studentId;
		
		ResponseEntity<Student> json;
		try {
			json = restTemplate.exchange(url,
			        HttpMethod.GET, entity, Student.class);
			
			 return json.getBody();
			 
		} catch (RestClientException e) {
			logger.error("Error returning Student Object",e.getStackTrace());
			return null;
		}
		  
		 
		
	}
	
	

}
