package com.example.gym_management.dto.response;

import java.util.List;

public record HealthRecordResponseDto(
        Long id,
        Double height,
        Double weight,
        List<PathologyResponseDto> pathologies
) {
}
