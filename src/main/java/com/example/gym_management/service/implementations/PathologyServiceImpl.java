package com.example.gym_management.service.implementations;

import com.example.gym_management.dto.request.PathologyRequestDto;
import com.example.gym_management.dto.response.PathologyResponseDto;
import com.example.gym_management.mapper.PathologyMapper;
import com.example.gym_management.model.Pathology;
import com.example.gym_management.repository.PathologyRepository;
import com.example.gym_management.service.interfaces.PathologyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
        Pathology pathology = pathologyMapper.toEntity(dto);
        return pathologyMapper.toDto(pathologyRepository.save(pathology));
    }

    @Override
    @Transactional
    public PathologyResponseDto update(Long id, PathologyRequestDto dto) {
        Pathology entity = pathologyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pathology not found"));

        entity.setName(dto.name());
        entity.setDescription(dto.description());

        return pathologyMapper.toDto(pathologyRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!pathologyRepository.existsById(id)) {
            throw new EntityNotFoundException("Pathology not found");
        }
        pathologyRepository.deleteById(id);
    }

    @Override
    public List<PathologyResponseDto> getAll() {
        return pathologyRepository.findAll()
                .stream().map(pathologyMapper::toDto)
                .toList();
    }

    @Override
    public PathologyResponseDto getById(Long id) {
        Pathology entity = pathologyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pathology not found"));
        return pathologyMapper.toDto(entity);
    }

    @Override
    public PathologyResponseDto getByName(String name) {
        Pathology entity = pathologyRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("Pathology not found"));
        return pathologyMapper.toDto(entity);
    }
}
