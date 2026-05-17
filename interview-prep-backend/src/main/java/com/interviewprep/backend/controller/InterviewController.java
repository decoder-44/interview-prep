package com.interviewprep.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviewprep.backend.dto.ApiResponse;
import com.interviewprep.backend.dto.InterviewDTO;
import com.interviewprep.backend.entity.Interview;
import com.interviewprep.backend.service.InterviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Interview>> createInterview(
            @Valid @RequestBody InterviewDTO interviewDTO) {
        Interview interview = interviewService.createInterview(interviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(interview, "Interview created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Interview>> getInterviewById(@PathVariable Long id) {
        Interview interview = interviewService.getInterviewById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));
        return ResponseEntity.ok(ApiResponse.success(interview, "Interview fetched successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Interview>>> getAllInterviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Interview> interviews = interviewService.getAllInterviews(pageable);
        return ResponseEntity.ok(ApiResponse.success(interviews, "Interviews fetched successfully"));
    }

    @GetMapping("/public/published")
    public ResponseEntity<ApiResponse<Page<Interview>>> getPublishedInterviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Interview> interviews = interviewService.getPublishedInterviews(pageable);
        return ResponseEntity.ok(ApiResponse.success(interviews, "Published interviews fetched successfully"));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<Page<Interview>>> getInterviewsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Interview> interviews = interviewService.getInterviewsByCategory(category, pageable);
        return ResponseEntity.ok(ApiResponse.success(interviews, "Interviews fetched successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<InterviewDTO>> updateInterview(
            @PathVariable Long id,
            @Valid @RequestBody InterviewDTO interviewDTO) {
        InterviewDTO updatedInterview = interviewService.updateInterview(id, interviewDTO);
        return ResponseEntity.ok(ApiResponse.success(updatedInterview, "Interview updated successfully"));
    }

    @PatchMapping("/{id}/publish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> publishInterview(@PathVariable Long id) {
        interviewService.publishInterview(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Interview published successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteInterview(@PathVariable Long id) {
        interviewService.deleteInterview(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Interview deleted successfully"));
    }
}
