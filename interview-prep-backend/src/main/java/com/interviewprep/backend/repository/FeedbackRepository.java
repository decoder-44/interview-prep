package com.interviewprep.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interviewprep.backend.entity.Feedback;
import com.interviewprep.backend.entity.InterviewSession;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByInterviewSession(InterviewSession interviewSession);
}
