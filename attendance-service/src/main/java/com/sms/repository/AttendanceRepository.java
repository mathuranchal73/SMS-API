package com.sms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sms.document.Attendance;

public interface AttendanceRepository extends MongoRepository<Attendance,Integer> {

}
