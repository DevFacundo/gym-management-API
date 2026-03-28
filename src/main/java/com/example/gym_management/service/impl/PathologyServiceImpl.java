package com.example.gym_management.service.impl;

import com.example.gym_management.dto.request.PathologyRequestDto;
import com.example.gym_management.dto.response.PathologyResponseDto;
import com.example.gym_management.exception.ResourceNotFoundException;
import com.example.gym_management.mapper.PathologyMapper;
import com.example.gym_management.model.Pathology;
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
    public PathologyResponseDto create(PathologyRequestDto dto) {
        Pathology pathology = Pathology.builder()
                .name(dto.name())
                .description(dto.description())
                .build();
        return pathologyMapper.toDto(pathologyRepository.save(pathology));
    }

    @Override
    public PathologyResponseDto update(Long id, PathologyRequestDto dto) {
        Pathology pathology = pathologyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pathology", id));

        pathology.setName(dto.name());
        pathology.setDescription(dto.description());

        return pathologyMapper.toDto(pathologyRepository.save(pathology));
    }

    @Override
    public void delete(Long id) {
        Pathology pathology = pathologyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pathology", id));
        pathologyRepository.delete(pathology);
    }

    @Override
    public List<PathologyResponseDto> getAll() {
        return pathologyRepository.findAll()
                .stream()
                .map(pathologyMapper::toDto)
                .toList();
    }

    @Override
    public PathologyResponseDto getById(Long id) {
        return pathologyRepository.findById(id)
                .map(pathologyMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Pathology", id));
    }

    @Override
    public PathologyResponseDto getByName(String name) {
        return pathologyRepository.findByName(name)
                .map(pathologyMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Pathology con nombre " + name + " no encontrada"));
    }
}
