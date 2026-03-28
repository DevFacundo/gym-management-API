package com.example.gym_management.dto.response;

public record PathologyResponseDto(
        Long id,
        String name,
        String description,
        String severity,
        String recommendations
) {
}
