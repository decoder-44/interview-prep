package com.interviewprep.backend.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public LocalDateTime parseDateTime(String dateString) {
        return LocalDateTime.parse(dateString, formatter);
    }

    public Long getTimeDifferenceInSeconds(LocalDateTime from, LocalDateTime to) {
        return java.time.temporal.ChronoUnit.SECONDS.between(from, to);
    }
}
