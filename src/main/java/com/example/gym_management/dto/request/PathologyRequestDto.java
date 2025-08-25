package com.example.gym_management.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PathologyRequestDto(

    @NotBlank (message = "Se debe ingresar un nombre de Patologia")
    String name,
    String description

) {
}
