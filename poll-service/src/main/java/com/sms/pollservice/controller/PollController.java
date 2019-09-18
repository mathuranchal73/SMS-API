package com.sms.pollservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sms.pollservice.model.Poll;
import com.sms.pollservice.payload.ApiResponse;
import com.sms.pollservice.payload.PagedResponse;
import com.sms.pollservice.payload.PollRequest;
import com.sms.pollservice.payload.PollResponse;
import com.sms.pollservice.payload.VoteRequest;
import com.sms.pollservice.repository.PollRepository;
import com.sms.pollservice.repository.UserRepository;
import com.sms.pollservice.repository.VoteRepository;
import com.sms.pollservice.security.CurrentUser;
import com.sms.pollservice.security.UserPrincipal;
import com.sms.pollservice.service.PollService;
import com.sms.pollservice.util.AppConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/polls")
@Api(value="polls", description = "Data service operations on Polls Service", tags=("poll-service"))
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollService pollService;

    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @GetMapping
    @ApiOperation(value="Gets All the Polls", notes="Returns all the Active Polls",produces = "application/json", nickname="getPolls")
    public PagedResponse<PollResponse> getPolls(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getAllPolls(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    @ApiOperation(value="Creates the Poll", notes="Creates a Poll",produces = "application/json", nickname="createPoll")
    public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequest pollRequest) {
        Poll poll = pollService.createPoll(pollRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pollId}")
                .buildAndExpand(poll.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Poll Created Successfully"));
    }


    @GetMapping("/{pollId}")
    @ApiOperation(value="Gets the Poll by Id", notes="Gets the Poll by Id",produces = "application/json", nickname="getPollById")
    public PollResponse getPollById(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long pollId) {
        return pollService.getPollById(pollId, currentUser);
    }

    @PostMapping("/{pollId}/votes")
    @PreAuthorize("hasRole('TEACHER')")
    @ApiOperation(value="Casts the vote for the provided Poll Id and current User", notes="Casts the vote for the provided Poll Id and current User",produces = "application/json", nickname="castVote")
    public PollResponse castVote(@CurrentUser UserPrincipal currentUser,
                         @PathVariable Long pollId,
                         @Valid @RequestBody VoteRequest voteRequest) {
        return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser);
    }

}