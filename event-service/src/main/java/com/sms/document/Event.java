package com.sms.document;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "Event")
public class Event {
	
	private String id;
	private String userID;
	private String eventContext;
	private String eventType;
	private Instant timeStamp;
	
	private EventDetails eventDetails;
	
	public EventDetails getEventDetails() {
		return eventDetails;
	}
	public void setEventDetails(EventDetails eventDetails) {
		this.eventDetails = eventDetails;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getEventContext() {
		return eventContext;
	}
	public void setEventContext(String eventContext) {
		this.eventContext = eventContext;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public Instant getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Instant timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	
	

}
