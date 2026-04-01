package com.example.gym_management.dto.response;

import java.time.LocalDate;

public record WeightRecordResponseDto(
        Long id,
        Double weight,
        Double height,
        LocalDate date,
        String notes) {}
