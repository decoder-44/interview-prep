package com.interviewprep.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviewprep.backend.dto.ApiResponse;
import com.interviewprep.backend.dto.QuestionCreateDTO;
import com.interviewprep.backend.dto.QuestionDTO;
import com.interviewprep.backend.entity.Question;
import com.interviewprep.backend.service.QuestionAiService;
import com.interviewprep.backend.service.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionAiService questionAiService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Question>> createQuestion(
            @Valid @RequestBody QuestionCreateDTO questionDTO) {
        Question question = questionService.createQuestion(questionDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(question, "Question created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Question>> getQuestionById(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        questionService.incrementViewCount(id);
        return ResponseEntity.ok(ApiResponse.success(question, "Question fetched successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Question>>> getAllQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questions = questionService.getAllQuestions(pageable);
        return ResponseEntity.ok(ApiResponse.success(questions, "Questions fetched successfully"));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<Page<Question>>> getQuestionsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questions = questionService.getQuestionsByCategory(category, pageable);
        return ResponseEntity.ok(ApiResponse.success(questions, "Questions fetched successfully"));
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<ApiResponse<Page<Question>>> getQuestionsByDifficulty(
            @PathVariable String difficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questions = questionService.getQuestionsByDifficulty(difficulty, pageable);
        return ResponseEntity.ok(ApiResponse.success(questions, "Questions fetched successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<QuestionDTO>> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody QuestionCreateDTO questionDTO) {
        QuestionDTO updatedQuestion = questionService.updateQuestion(id, questionDTO);
        return ResponseEntity.ok(ApiResponse.success(updatedQuestion, "Question updated successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Question deleted successfully"));
    }

    @PostMapping("/generate-ai")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Question>>> generateQuestionsWithAi(
            @RequestParam String difficulty,
            @RequestParam String category,
            @RequestParam(defaultValue = "5") Integer numberOfQuestions) {

        List<QuestionCreateDTO> generatedQuestions = questionAiService.generateQuestionsForInterview(
                category, difficulty, numberOfQuestions);

        List<Question> savedQuestions = new ArrayList<>();
        for (QuestionCreateDTO questionDto : generatedQuestions) {
            Question question = questionService.createQuestion(questionDto);
            savedQuestions.add(question);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(savedQuestions,
                        numberOfQuestions + " questions generated and saved successfully"));
    }
}
