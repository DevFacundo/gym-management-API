package com.example.gym_management.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record WeightRecordRequestDto(
        @NotNull(message = "El peso es obligatorio")
        @Positive(message = "El peso debe ser positivo")
        Double weight,
        // Altura opcional — solo si cambió
        Double height,
        // Si no se manda, se usa la fecha de hoy (@PrePersist)
        LocalDate date,
        String notes,
        @NotNull(message = "El health record es obligatorio")
        Long healthRecordId
) {
}
