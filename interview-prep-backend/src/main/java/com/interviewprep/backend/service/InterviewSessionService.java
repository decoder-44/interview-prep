package com.interviewprep.backend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.interviewprep.backend.dto.InterviewSessionDTO;
import com.interviewprep.backend.entity.InterviewSession;

public interface InterviewSessionService {
    InterviewSession startInterviewSession(Long userId, Long interviewId);
    Optional<InterviewSession> getSessionById(Long id);
    Page<InterviewSession> getUserSessions(Long userId, Pageable pageable);
    InterviewSession completeSession(Long sessionId, Double score);
    InterviewSessionDTO updateSessionProgress(Long sessionId, Integer questionsAttempted, Integer questionsCorrect);
}
