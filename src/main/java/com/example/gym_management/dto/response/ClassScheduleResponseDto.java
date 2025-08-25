package com.example.gym_management.dto.response;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public record ClassScheduleResponseDto(
        Long id,
        DayOfWeek day,
        LocalTime startTime,
        LocalTime endTime,
        Integer maxCapacity,
        List<Long> memberIds){}
