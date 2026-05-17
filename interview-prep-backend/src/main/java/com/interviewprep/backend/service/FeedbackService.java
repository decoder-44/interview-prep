package com.interviewprep.backend.service;

import java.util.List;

import com.interviewprep.backend.dto.FeedbackDTO;
import com.interviewprep.backend.entity.Feedback;

public interface FeedbackService {
    Feedback saveFeedback(FeedbackDTO feedbackDTO);
    List<Feedback> getSessionFeedback(Long sessionId);
    Feedback updateFeedback(Long feedbackId, FeedbackDTO feedbackDTO);
    void deleteFeedback(Long feedbackId);
}
