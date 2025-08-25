package com.example.gym_management.dto.response;

import java.math.BigDecimal;

public record MembershipPlanResponseDto(
        Long id,
        String name,
        BigDecimal price
) {
}
