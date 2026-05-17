package com.interviewprep.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interviewprep.backend.entity.PerformanceMetrics;
import com.interviewprep.backend.entity.User;

@Repository
public interface PerformanceMetricsRepository extends JpaRepository<PerformanceMetrics, Long> {
    List<PerformanceMetrics> findByUser(User user);
    List<PerformanceMetrics> findByUserAndCategory(User user, String category);
}
