package com.interviewprep.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviewprep.backend.dto.ApiResponse;
import com.interviewprep.backend.entity.InterviewSession;
import com.interviewprep.backend.service.InterviewSessionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/interview-sessions")
@RequiredArgsConstructor
public class InterviewSessionController {

    private final InterviewSessionService interviewSessionService;

    @PostMapping("/start/{userId}/{interviewId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<InterviewSession>> startInterviewSession(
            @PathVariable Long userId,
            @PathVariable Long interviewId) {
        InterviewSession session = interviewSessionService.startInterviewSession(userId, interviewId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(session, "Interview session started successfully"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<InterviewSession>> getSessionById(@PathVariable Long id) {
        InterviewSession session = interviewSessionService.getSessionById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        return ResponseEntity.ok(ApiResponse.success(session, "Session fetched successfully"));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Page<InterviewSession>>> getUserSessions(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InterviewSession> sessions = interviewSessionService.getUserSessions(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(sessions, "User sessions fetched successfully"));
    }

    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<InterviewSession>> completeSession(
            @PathVariable Long id,
            @RequestParam Double score) {
        InterviewSession session = interviewSessionService.completeSession(id, score);
        return ResponseEntity.ok(ApiResponse.success(session, "Session completed successfully"));
    }
}
