package com.sms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sms.document.GlobalMessage;

public interface GlobalMessageRepository extends MongoRepository<GlobalMessage,Long> {

}
