package com.example.gym_management.service.interfaces;

import com.example.gym_management.dto.request.PathologyRequestDto;
import com.example.gym_management.dto.response.PathologyResponseDto;

import java.util.List;

public interface PathologyService {
    PathologyResponseDto create(PathologyRequestDto pathologyRequestDto);
    PathologyResponseDto update(Long id, PathologyRequestDto pathologyRequestDto);
    void delete(Long id);
    List<PathologyResponseDto> getAll();
    PathologyResponseDto getById(Long id);
    PathologyResponseDto getByName(String name);
}
