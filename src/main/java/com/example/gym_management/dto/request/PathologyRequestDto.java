package com.example.gym_management.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PathologyRequestDto(

    @NotBlank (message = "name pathology is required")
    String name,
    String description

) {
}
