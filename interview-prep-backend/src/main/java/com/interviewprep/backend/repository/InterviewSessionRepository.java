package com.interviewprep.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interviewprep.backend.entity.InterviewSession;
import com.interviewprep.backend.entity.User;

@Repository
public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long> {
    Page<InterviewSession> findByUser(User user, Pageable pageable);
    Page<InterviewSession> findByUserAndStatus(User user, String status, Pageable pageable);
}
