package com.example.gym_management.dto.response;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ClassScheduleResponseDto(
        Long id,
        DayOfWeek day,
        LocalTime startTime,
        LocalTime endTime,
        int maxCapacity,
        int currentOccupancy
) {}
