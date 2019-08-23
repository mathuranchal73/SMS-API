package com.sms.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.sms.kafka.Receiver;

public class ReceiveRecords implements Tasklet{
	
	private static Logger logger=LoggerFactory.getLogger(ReceiveRecords.class);
	
	@Autowired
	Receiver receiver;
	

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		
		
		return null;
	}
	
	

}
