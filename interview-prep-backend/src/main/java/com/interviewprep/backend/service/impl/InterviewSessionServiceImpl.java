package com.interviewprep.backend.service.impl;

import com.interviewprep.backend.dto.InterviewSessionDTO;
import com.interviewprep.backend.entity.InterviewSession;
import com.interviewprep.backend.entity.User;
import com.interviewprep.backend.exception.ResourceNotFoundException;
import com.interviewprep.backend.repository.InterviewRepository;
import com.interviewprep.backend.repository.InterviewSessionRepository;
import com.interviewprep.backend.repository.UserRepository;
import com.interviewprep.backend.service.InterviewSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterviewSessionServiceImpl implements InterviewSessionService {
    private final InterviewSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final InterviewRepository interviewRepository;

    @Override
    public InterviewSession startInterviewSession(Long userId, Long interviewId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found"));

        InterviewSession session = InterviewSession.builder()
                .user(user)
                .interview(interview)
                .status("IN_PROGRESS")
                .build();

        return sessionRepository.save(session);
    }

    @Override
    public Optional<InterviewSession> getSessionById(Long id) {
        return sessionRepository.findById(id);
    }

    @Override
    public Page<InterviewSession> getUserSessions(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return sessionRepository.findByUser(user, pageable);
    }

    @Override
    public InterviewSession completeSession(Long sessionId, Double score) {
        InterviewSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        session.setStatus("COMPLETED");
        session.setCompletedAt(LocalDateTime.now());
        session.setOverallScore(score);

        return sessionRepository.save(session);
    }

    @Override
    public InterviewSessionDTO updateSessionProgress(Long sessionId, Integer questionsAttempted, Integer questionsCorrect) {
        InterviewSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        session.setQuestionsAttempted(questionsAttempted);
        session.setQuestionsCorrect(questionsCorrect);

        InterviewSession updatedSession = sessionRepository.save(session);
        return mapToDTO(updatedSession);
    }

    private InterviewSessionDTO mapToDTO(InterviewSession session) {
        return InterviewSessionDTO.builder()
                .id(session.getId())
                .interviewId(session.getInterview().getId())
                .status(session.getStatus())
                .questionsAttempted(session.getQuestionsAttempted())
                .questionsCorrect(session.getQuestionsCorrect())
                .overallScore(session.getOverallScore())
                .totalTimeSpentSeconds(session.getTotalTimeSpentSeconds())
                .build();
    }
}
