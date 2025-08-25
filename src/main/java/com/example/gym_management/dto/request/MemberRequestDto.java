package com.example.gym_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record MemberRequestDto(
    @NotBlank(message = "Se debe ingresar un nombre")
    @Size(min = 2, max=20)
     String firstName,

    @NotBlank(message = "Se debe ingresar un apellido")
    @Size(min = 2, max=20)
     String lastName,

    @NotBlank(message = "Se debe ingresar un numero de celular")
    @Size(min = 9, max=20)
     String phoneNumber,

    String auxiliaryPhoneNumber,
    Double height,
    Double weight,
    LocalDate birthDate,
    LocalDate signUpDate,
    List<Long> paymentsId,
    List<Long> pathologiesId
) {
}
