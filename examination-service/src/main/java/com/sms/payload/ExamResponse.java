package com.sms.payload;

import java.time.Instant;
import java.util.List;

import com.sms.payload.UserSummary;

public class ExamResponse {
	
	private Long id;
    private String question;
    private List<ChoiceResponse> choices;
    private UserSummary createdBy;
    private Instant creationDateTime;
    private Instant expirationDateTime;
    private Boolean isExpired;

}
