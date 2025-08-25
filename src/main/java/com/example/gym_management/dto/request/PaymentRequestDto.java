package com.example.gym_management.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PaymentRequestDto(
    @Min(value =0, message = "El monto abonado debe ser mayor a 0" )
    Double amount,
    LocalDate paymentDate,
    @NotNull
    Long memberId,
    @NotNull
    Long membershipPlanId
) {
}
