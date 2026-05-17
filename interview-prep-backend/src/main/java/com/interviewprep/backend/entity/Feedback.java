package com.interviewprep.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_session_id", nullable = false)
    private InterviewSession interviewSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String feedbackContent;

    @Column
    @Builder.Default
    private Integer rating = 0; // 1-5 stars

    @Column(columnDefinition = "TEXT")
    private String suggestions;

    @Column(name = "is_ai_generated")
    @Builder.Default
    private Boolean isAiGenerated = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}