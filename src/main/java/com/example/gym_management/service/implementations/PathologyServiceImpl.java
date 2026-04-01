package com.example.gym_management.service.implementations;

import com.example.gym_management.dto.request.PathologyRequestDto;
import com.example.gym_management.dto.response.PathologyResponseDto;
import com.example.gym_management.mapper.PathologyMapper;
import com.example.gym_management.model.HealthRecord;
import com.example.gym_management.model.Pathology;
import com.example.gym_management.repository.HealthRecordRepository;
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
    private final HealthRecordRepository healthRecordRepository;
    private final PathologyMapper pathologyMapper;

    @Override
    @Transactional
    public PathologyResponseDto create(PathologyRequestDto dto) {
        HealthRecord healthRecord = healthRecordRepository.findById(dto.healthRecordId())
                .orElseThrow(() -> new EntityNotFoundException("HealthRecord no encontrado"));

        Pathology pathology = pathologyMapper.toEntity(dto);
        pathology.setHealthRecord(healthRecord);
        pathology.setActive(true);

        return pathologyMapper.toDto(pathologyRepository.save(pathology));
    }

    @Override
    @Transactional
    public PathologyResponseDto update(Long id, PathologyRequestDto dto) {
        Pathology entity = pathologyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pathology no encontrada"));

        entity.setName(dto.name());
        if (dto.notes() != null) entity.setNotes(dto.notes());
        if (dto.date()  != null) entity.setDate(dto.date());

        return pathologyMapper.toDto(pathologyRepository.save(entity));
    }

    @Override
    @Transactional
    public PathologyResponseDto toggleActive(Long id) {
        Pathology entity = pathologyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pathology no encontrada"));

        entity.setActive(!entity.getActive());
        return pathologyMapper.toDto(pathologyRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!pathologyRepository.existsById(id)) {
            throw new EntityNotFoundException("Pathology no encontrada");
        }
        pathologyRepository.deleteById(id);
    }

    @Override
    public List<PathologyResponseDto> getByHealthRecordId(Long healthRecordId) {
        return pathologyRepository
                .findByHealthRecordIdOrderByDateDesc(healthRecordId)
                .stream().map(pathologyMapper::toDto).toList();
    }

    @Override
    public PathologyResponseDto getById(Long id) {
        return pathologyRepository.findById(id)
                .map(pathologyMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Pathology no encontrada"));
    }
}
