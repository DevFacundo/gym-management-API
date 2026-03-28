package com.example.gym_management.dto.request;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ClassScheduleRequestDto(
        DayOfWeek day,
        LocalTime startTime,
        LocalTime endTime,
        Integer maxCapacity
) {}
