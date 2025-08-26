package com.example.gym_management.mapper;

import com.example.gym_management.dto.request.ClassScheduleRequestDto;
import com.example.gym_management.dto.response.ClassScheduleResponseDto;
import com.example.gym_management.model.ClassSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ClassScheduleMapper {
    ClassSchedule toEntity (ClassScheduleRequestDto classScheduleRequestDto);
    @Mapping(target = "memberIds", expression = "java(classSchedule.getMembers().stream().map(m -> m.getId()).distinct().toList())")
    ClassScheduleResponseDto toDto (ClassSchedule classSchedule);
}
