package com.interviewprep.backend.service.impl;

import com.interviewprep.backend.dto.QuestionCreateDTO;
import com.interviewprep.backend.dto.QuestionDTO;
import com.interviewprep.backend.entity.Question;
import com.interviewprep.backend.exception.ResourceNotFoundException;
import com.interviewprep.backend.repository.QuestionRepository;
import com.interviewprep.backend.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Override
    public Question createQuestion(QuestionCreateDTO questionDTO) {
        Question question = Question.builder()
                .title(questionDTO.getTitle())
                .description(questionDTO.getDescription())
                .hints(questionDTO.getHints())
                .expectedAnswer(questionDTO.getExpectedAnswer())
                .category(questionDTO.getCategory())
                .difficulty(questionDTO.getDifficulty())
                .estimatedTimeMinutes(questionDTO.getEstimatedTimeMinutes())
                .build();

        return questionRepository.save(question);
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Page<Question> getAllQuestions(Pageable pageable) {
        return questionRepository.findByIsActiveTrue(pageable);
    }

    @Override
    public Page<Question> getQuestionsByCategory(String category, Pageable pageable) {
        return questionRepository.findByCategory(category, pageable);
    }

    @Override
    public Page<Question> getQuestionsByDifficulty(String difficulty, Pageable pageable) {
        return questionRepository.findByDifficulty(difficulty, pageable);
    }

    @Override
    public QuestionDTO updateQuestion(Long id, QuestionCreateDTO questionDTO) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        question.setTitle(questionDTO.getTitle());
        question.setDescription(questionDTO.getDescription());
        question.setHints(questionDTO.getHints());
        question.setExpectedAnswer(questionDTO.getExpectedAnswer());
        question.setCategory(questionDTO.getCategory());
        question.setDifficulty(questionDTO.getDifficulty());
        question.setEstimatedTimeMinutes(questionDTO.getEstimatedTimeMinutes());

        Question updatedQuestion = questionRepository.save(question);
        return mapToDTO(updatedQuestion);
    }

    @Override
    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        question.setIsActive(false);
        questionRepository.save(question);
    }

    @Override
    public void incrementViewCount(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        question.setViewCount(question.getViewCount() + 1);
        questionRepository.save(question);
    }

    private QuestionDTO mapToDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .title(question.getTitle())
                .description(question.getDescription())
                .hints(question.getHints())
                .expectedAnswer(question.getExpectedAnswer())
                .category(question.getCategory())
                .difficulty(question.getDifficulty())
                .estimatedTimeMinutes(question.getEstimatedTimeMinutes())
                .viewCount(question.getViewCount())
                .averageRating(question.getAverageRating())
                .build();
    }
}
