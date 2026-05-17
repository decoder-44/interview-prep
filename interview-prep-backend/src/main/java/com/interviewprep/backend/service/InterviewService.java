package com.interviewprep.backend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.interviewprep.backend.dto.InterviewDTO;
import com.interviewprep.backend.entity.Interview;

public interface InterviewService {
    Interview createInterview(InterviewDTO interviewDTO);
    Optional<Interview> getInterviewById(Long id);
    Page<Interview> getAllInterviews(Pageable pageable);
    Page<Interview> getPublishedInterviews(Pageable pageable);
    Page<Interview> getInterviewsByCategory(String category, Pageable pageable);
    InterviewDTO updateInterview(Long id, InterviewDTO interviewDTO);
    void deleteInterview(Long id);
    void publishInterview(Long id);
}
