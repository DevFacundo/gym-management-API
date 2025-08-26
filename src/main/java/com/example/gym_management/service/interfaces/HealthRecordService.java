package com.example.gym_management.service.interfaces;

import com.example.gym_management.dto.request.HealthRecordRequestDto;
import com.example.gym_management.dto.response.HealthRecordResponseDto;

import java.util.List;

public interface HealthRecordService {
    HealthRecordResponseDto create (HealthRecordRequestDto healthRecordRequestDto);
    HealthRecordResponseDto update (Long id, HealthRecordRequestDto healthRecordRequestDto);
    void delete (Long id);
    HealthRecordResponseDto getById (Long id);
    List<HealthRecordResponseDto> getAll();
}
