package com.interviewprep.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "interview_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    @Column(nullable = false)
    @Builder.Default
    private String status = "IN_PROGRESS"; // IN_PROGRESS, COMPLETED, ABANDONED

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "total_time_spent_seconds")
    @Builder.Default
    private Long totalTimeSpentSeconds = 0L;

    @Column(name = "questions_attempted")
    @Builder.Default
    private Integer questionsAttempted = 0;

    @Column(name = "questions_correct")
    @Builder.Default
    private Integer questionsCorrect = 0;

    @Column(name = "overall_score")
    @Builder.Default
    private Double overallScore = 0.0;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @PrePersist
    protected void onCreate() {
        startedAt = LocalDateTime.now();
    }
}