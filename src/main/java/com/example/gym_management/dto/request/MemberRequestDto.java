package com.example.gym_management.dto.request;

import jakarta.validation.constraints.NotBlank;
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

    String auxiliaryPhoneNumber,
    LocalDate birthDate,
    LocalDate signUpDate,
    List<Long> paymentsId,
    //quitar ->
    List<Long> pathologiesId
) {
}
