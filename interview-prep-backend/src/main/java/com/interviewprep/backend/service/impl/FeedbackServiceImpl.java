package com.interviewprep.backend.service.impl;

import com.interviewprep.backend.dto.FeedbackDTO;
import com.interviewprep.backend.entity.Feedback;
import com.interviewprep.backend.entity.Question;
import com.interviewprep.backend.entity.InterviewSession;
import com.interviewprep.backend.exception.ResourceNotFoundException;
import com.interviewprep.backend.repository.FeedbackRepository;
import com.interviewprep.backend.repository.QuestionRepository;
import com.interviewprep.backend.repository.InterviewSessionRepository;
import com.interviewprep.backend.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final InterviewSessionRepository sessionRepository;
    private final QuestionRepository questionRepository;

    @Override
    public Feedback saveFeedback(FeedbackDTO feedbackDTO) {
        InterviewSession session = sessionRepository.findById(feedbackDTO.getInterviewSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Interview session not found"));

        Question question = null;
        if (feedbackDTO.getQuestionId() != null) {
            question = questionRepository.findById(feedbackDTO.getQuestionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        }

        Feedback feedback = Feedback.builder()
                .interviewSession(session)
                .question(question)
                .feedbackContent(feedbackDTO.getFeedbackContent())
                .rating(feedbackDTO.getRating())
                .suggestions(feedbackDTO.getSuggestions())
                .isAiGenerated(feedbackDTO.getIsAiGenerated())
                .build();

        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getSessionFeedback(Long sessionId) {
        InterviewSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview session not found"));
        return feedbackRepository.findByInterviewSession(session);
    }

    @Override
    public Feedback updateFeedback(Long feedbackId, FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));

        feedback.setFeedbackContent(feedbackDTO.getFeedbackContent());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setSuggestions(feedbackDTO.getSuggestions());

        return feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));
        feedbackRepository.delete(feedback);
    }
}
