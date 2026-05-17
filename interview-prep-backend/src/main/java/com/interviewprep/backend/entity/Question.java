package com.interviewprep.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Question title is required")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Question description is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String hints;

    @Column(columnDefinition = "TEXT")
    private String expectedAnswer;

    @NotBlank(message = "Category is required")
    @Column(nullable = false)
    private String category; // e.g., "Java", "System Design", "SQL", "Behavioral"

    @Column(nullable = false)
    @Builder.Default
    private String difficulty = "MEDIUM"; // EASY, MEDIUM, HARD

    @Column(nullable = false)
    @Builder.Default
    private Integer estimatedTimeMinutes = 5;

    @Column(nullable = false)
    @Builder.Default
    private Long viewCount = 0L;

    @Column(nullable = false)
    @Builder.Default
    private Double averageRating = 0.0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}