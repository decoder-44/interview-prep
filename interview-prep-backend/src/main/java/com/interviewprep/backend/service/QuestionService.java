package com.interviewprep.backend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.interviewprep.backend.dto.QuestionCreateDTO;
import com.interviewprep.backend.dto.QuestionDTO;
import com.interviewprep.backend.entity.Question;

public interface QuestionService {
    Question createQuestion(QuestionCreateDTO questionDTO);
    Optional<Question> getQuestionById(Long id);
    Page<Question> getAllQuestions(Pageable pageable);
    Page<Question> getQuestionsByCategory(String category, Pageable pageable);
    Page<Question> getQuestionsByDifficulty(String difficulty, Pageable pageable);
    QuestionDTO updateQuestion(Long id, QuestionCreateDTO questionDTO);
    void deleteQuestion(Long id);
    void incrementViewCount(Long id);
}
