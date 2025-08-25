package com.example.gym_management.mapper;

import com.example.gym_management.dto.request.ClassScheduleRequestDto;
import com.example.gym_management.dto.response.ClassScheduleResponseDto;
import com.example.gym_management.model.ClassSchedule;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ClassScheduleMapper {
    ClassSchedule toEntity (ClassScheduleRequestDto classScheduleRequestDto);
    ClassScheduleResponseDto toDto (ClassSchedule classSchedule);
}
