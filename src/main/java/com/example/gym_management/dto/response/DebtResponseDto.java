package com.example.gym_management.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DebtResponseDto(
        Long memberId,
        String memberName,
        Long monthsOwed,
        BigDecimal totalDebtAmount,
        LocalDate lastExpirationDate
) {}
