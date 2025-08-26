package com.example.gym_management.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record HealthRecordRequestDto(
        @NotNull Double height,
        @NotNull Double weight,
        List<Long> pathologyId
) {
}
