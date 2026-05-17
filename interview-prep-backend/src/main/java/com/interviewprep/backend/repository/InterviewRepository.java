package com.interviewprep.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interviewprep.backend.entity.Interview;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    Page<Interview> findByCategory(String category, Pageable pageable);
    Page<Interview> findByIsPublishedTrue(Pageable pageable);
}
