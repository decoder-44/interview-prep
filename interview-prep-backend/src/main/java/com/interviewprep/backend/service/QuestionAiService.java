package com.interviewprep.backend.service;

import java.util.List;

import com.interviewprep.backend.dto.QuestionCreateDTO;

public interface QuestionAiService {
    List<QuestionCreateDTO> generateQuestionsForInterview(String category, String difficulty, Integer numberOfQuestions);
    String generateExpectedAnswer(String questionTitle, String category);
}
