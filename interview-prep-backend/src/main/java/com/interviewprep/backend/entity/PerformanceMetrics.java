package com.interviewprep.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "performance_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_session_id")
    private InterviewSession interviewSession;

    @Column(nullable = false)
    @Builder.Default
    private Double accuracyPercentage = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Double speedScore = 0.0; // Based on time taken vs estimated

    @Column(nullable = false)
    @Builder.Default
    private Double communicationScore = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Double problemSolvingScore = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Double overallScore = 0.0;

    @Column(name = "category")
    private String category;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}