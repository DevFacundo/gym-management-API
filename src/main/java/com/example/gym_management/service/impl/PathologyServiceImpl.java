package com.example.gym_management.service.impl;

import com.example.gym_management.dto.request.PathologyRequestDto;
import com.example.gym_management.dto.response.PathologyResponseDto;
import com.example.gym_management.mapper.PathologyMapper;
import com.example.gym_management.repository.PathologyRepository;
import com.example.gym_management.service.PathologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PathologyServiceImpl implements PathologyService {

    private final PathologyRepository pathologyRepository;
    private final PathologyMapper pathologyMapper;


    @Override
    public PathologyResponseDto create(PathologyRequestDto pathologyRequestDto) {
        return null;
    }

    @Override
    public PathologyResponseDto update(Long id, PathologyRequestDto pathologyRequestDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<PathologyResponseDto> getAll() {
        return List.of();
    }

    @Override
    public PathologyResponseDto getById(Long id) {
        return null;
    }

    @Override
    public PathologyResponseDto getByName(String name) {
        return null;
    }
}
