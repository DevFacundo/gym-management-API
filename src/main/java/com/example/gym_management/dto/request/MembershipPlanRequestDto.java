package com.example.gym_management.dto.request;

import com.example.gym_management.enums.PlanName;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MembershipPlanRequestDto(
        @NotBlank(message = "name of membership plan is required")
        PlanName planName,
        @NotNull(message = "price is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "price must be greater than or equal to 0")
        BigDecimal price
) {
}
