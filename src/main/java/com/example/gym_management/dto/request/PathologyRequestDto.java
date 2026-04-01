package com.example.gym_management.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record PathologyRequestDto(
        @NotBlank(message = "Name is required")
        String name,
        String notes,
        LocalDate date,
        Long healthRecordId
) {}
