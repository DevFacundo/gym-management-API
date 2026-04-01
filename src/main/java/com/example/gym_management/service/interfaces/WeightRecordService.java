package com.example.gym_management.service.interfaces;

import com.example.gym_management.dto.request.WeightRecordRequestDto;
import com.example.gym_management.dto.response.WeightRecordResponseDto;

import java.util.List;

public interface WeightRecordService {
    WeightRecordResponseDto create(WeightRecordRequestDto dto);
    WeightRecordResponseDto update(Long id, WeightRecordRequestDto dto);
    void delete(Long id);
    List<WeightRecordResponseDto> getByHealthRecordId(Long healthRecordId);
}
