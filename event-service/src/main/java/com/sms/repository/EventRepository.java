package com.sms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sms.document.Event;

public interface EventRepository extends MongoRepository<Event,String> {

}
