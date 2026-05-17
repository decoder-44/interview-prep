package com.interviewprep.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interviewprep.backend.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByCategory(String category, Pageable pageable);
    Page<Question> findByDifficulty(String difficulty, Pageable pageable);
    Page<Question> findByIsActiveTrue(Pageable pageable);
}
