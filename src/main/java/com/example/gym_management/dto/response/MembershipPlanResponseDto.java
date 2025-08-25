package com.example.gym_management.dto.response;

import com.example.gym_management.enums.PlanName;

import java.math.BigDecimal;

public record MembershipPlanResponseDto(
        Long id,
        PlanName planName,
        BigDecimal price
) {
}
