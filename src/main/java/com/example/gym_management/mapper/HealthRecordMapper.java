package com.example.gym_management.mapper;

import com.example.gym_management.dto.request.HealthRecordRequestDto;
import com.example.gym_management.dto.response.HealthRecordResponseDto;
import com.example.gym_management.model.HealthRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface HealthRecordMapper {
    HealthRecord toEntity(HealthRecordRequestDto healthRecordRequestDto);
    HealthRecordResponseDto toDto(HealthRecord healthRecord);
}
