package com.example.gym_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record MemberRequestDto(
    @NotBlank(message = "name is required")
    @Size(min = 2, max=20)
     String firstName,

    @NotBlank(message = "lastname is required")
    @Size(min = 2, max=20)
     String lastName,

    @NotBlank(message = "phone number is required")
    @Size(min = 9, max=20)
     String phoneNumber,
    @NotBlank(message = "Dni number is required")
    @Pattern(regexp = "^(?!\\s*$).+", message = "El DNI no puede estar vacío.")
    @Size(min = 7, max = 8, message = "El DNI debe tener entre 7 y 8 dígitos.")
    @Pattern(regexp = "^[0-9]{7,8}$", message = "El DNI solo puede contener números y debe tener entre 7 y 8 dígitos.")
    String dni,
    String auxiliaryPhoneNumber,
    LocalDate birthDate,
    LocalDate signUpDate,
    HealthRecordRequestDto healthRecord,
    List<Long> classScheduleId
) {
}
