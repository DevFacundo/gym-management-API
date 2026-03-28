package com.example.gym_management.dto.request;

import java.util.List;

public record HealthRecordRequestDto(
        Double height,
        Double weight,
        List<Long> pathologyIds
) {
}
