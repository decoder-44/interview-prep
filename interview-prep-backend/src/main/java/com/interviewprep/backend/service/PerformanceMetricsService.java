package com.interviewprep.backend.service;

import java.util.List;

import com.interviewprep.backend.dto.PerformanceMetricsDTO;
import com.interviewprep.backend.entity.PerformanceMetrics;

public interface PerformanceMetricsService {
    PerformanceMetrics saveMetrics(Long userId, PerformanceMetricsDTO metricsDTO);
    List<PerformanceMetrics> getUserMetrics(Long userId);
    List<PerformanceMetrics> getUserMetricsByCategory(Long userId, String category);
    Double getUserAverageScore(Long userId);
}
