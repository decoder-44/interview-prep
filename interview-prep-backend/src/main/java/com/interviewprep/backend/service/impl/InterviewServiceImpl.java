package com.interviewprep.backend.service.impl;

import com.interviewprep.backend.dto.InterviewDTO;
import com.interviewprep.backend.entity.Interview;
import com.interviewprep.backend.exception.ResourceNotFoundException;
import com.interviewprep.backend.repository.InterviewRepository;
import com.interviewprep.backend.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {
    private final InterviewRepository interviewRepository;

    @Override
    public Interview createInterview(InterviewDTO interviewDTO) {
        Interview interview = Interview.builder()
                .title(interviewDTO.getTitle())
                .description(interviewDTO.getDescription())
                .category(interviewDTO.getCategory())
                .difficulty(interviewDTO.getDifficulty())
                .totalQuestions(interviewDTO.getTotalQuestions())
                .estimatedDurationMinutes(interviewDTO.getEstimatedDurationMinutes())
                .build();

        return interviewRepository.save(interview);
    }

    @Override
    public Optional<Interview> getInterviewById(Long id) {
        return interviewRepository.findById(id);
    }

    @Override
    public Page<Interview> getAllInterviews(Pageable pageable) {
        return interviewRepository.findAll(pageable);
    }

    @Override
    public Page<Interview> getPublishedInterviews(Pageable pageable) {
        return interviewRepository.findByIsPublishedTrue(pageable);
    }

    @Override
    public Page<Interview> getInterviewsByCategory(String category, Pageable pageable) {
        return interviewRepository.findByCategory(category, pageable);
    }

    @Override
    public InterviewDTO updateInterview(Long id, InterviewDTO interviewDTO) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found"));

        interview.setTitle(interviewDTO.getTitle());
        interview.setDescription(interviewDTO.getDescription());
        interview.setCategory(interviewDTO.getCategory());
        interview.setDifficulty(interviewDTO.getDifficulty());
        interview.setTotalQuestions(interviewDTO.getTotalQuestions());
        interview.setEstimatedDurationMinutes(interviewDTO.getEstimatedDurationMinutes());

        Interview updatedInterview = interviewRepository.save(interview);
        return mapToDTO(updatedInterview);
    }

    @Override
    public void deleteInterview(Long id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found"));
        interviewRepository.delete(interview);
    }

    @Override
    public void publishInterview(Long id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found"));
        interview.setIsPublished(true);
        interviewRepository.save(interview);
    }

    private InterviewDTO mapToDTO(Interview interview) {
        return InterviewDTO.builder()
                .id(interview.getId())
                .title(interview.getTitle())
                .description(interview.getDescription())
                .category(interview.getCategory())
                .difficulty(interview.getDifficulty())
                .totalQuestions(interview.getTotalQuestions())
                .estimatedDurationMinutes(interview.getEstimatedDurationMinutes())
                .isPublished(interview.getIsPublished())
                .attemptCount(interview.getAttemptCount())
                .build();
    }
}
