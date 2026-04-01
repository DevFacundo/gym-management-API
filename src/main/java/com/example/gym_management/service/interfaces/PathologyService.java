package com.example.gym_management.service.interfaces;

import com.example.gym_management.dto.request.PathologyRequestDto;
import com.example.gym_management.dto.response.PathologyResponseDto;

import java.util.List;

public interface PathologyService {
    PathologyResponseDto create(PathologyRequestDto dto);
    PathologyResponseDto update(Long id, PathologyRequestDto dto);
    PathologyResponseDto toggleActive(Long id);
    void delete(Long id);
    List<PathologyResponseDto> getByHealthRecordId(Long healthRecordId);
    PathologyResponseDto getById(Long id);
}
