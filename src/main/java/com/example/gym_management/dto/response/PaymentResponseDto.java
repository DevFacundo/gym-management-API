package com.example.gym_management.dto.response;

import java.time.LocalDate;

public record PaymentResponseDto(
        Long id,
        Double amount,
        LocalDate paymentDate,
        LocalDate expirationDate,
        Long memberId
) {
}
