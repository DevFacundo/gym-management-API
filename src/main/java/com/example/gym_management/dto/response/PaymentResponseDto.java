package com.example.gym_management.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentResponseDto(
        Long id,
        BigDecimal amount,
        LocalDate paymentDate,
        LocalDate expirationDate,
        Long memberId,
        Long membershipPlanId
) {
}
