package com.example.gym_management.service.implementations;

import com.example.gym_management.dto.request.HealthRecordRequestDto;
import com.example.gym_management.dto.response.HealthRecordResponseDto;
import com.example.gym_management.mapper.HealthRecordMapper;
import com.example.gym_management.model.HealthRecord;
import com.example.gym_management.repository.HealthRecordRepository;
import com.example.gym_management.repository.PathologyRepository;
import com.example.gym_management.service.interfaces.HealthRecordService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthRecordServiceImpl implements HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;
    private final PathologyRepository pathologyRepository;
    private final HealthRecordMapper healthRecordMapper;

    @Override
    public HealthRecordResponseDto create(HealthRecordRequestDto healthRecordRequestDto) {
        HealthRecord healthRecord = healthRecordMapper.toEntity(healthRecordRequestDto);

        if (healthRecordRequestDto.pathologyId() != null && !healthRecordRequestDto.pathologyId().isEmpty()) {
            healthRecord.setPathologies(pathologyRepository.findAllById(healthRecordRequestDto.pathologyId()));
        }

        return healthRecordMapper.toDto(healthRecordRepository.save(healthRecord));
    }

    @Override
    @Transactional
    public HealthRecordResponseDto update(Long id, HealthRecordRequestDto dto) {
        HealthRecord entity = healthRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("HealthRecord not found"));

        if (dto.height() != null) entity.setHeight(dto.height());
        if (dto.weight() != null) entity.setWeight(dto.weight());
        if (dto.pathologyId() != null) {
            entity.setPathologies(pathologyRepository.findAllById(dto.pathologyId()));
        }

        return healthRecordMapper.toDto(healthRecordRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!healthRecordRepository.existsById(id)) {
            throw new EntityNotFoundException("HealthRecord not found");
        }
        healthRecordRepository.deleteById(id);
    }

    @Override
    public HealthRecordResponseDto getById(Long id) {
        HealthRecord entity = healthRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("HealthRecord not found"));
        return healthRecordMapper.toDto(entity);
    }

    @Override
    public List<HealthRecordResponseDto> getAll() {
        return healthRecordRepository.findAll()
                .stream().map(healthRecordMapper::toDto)
                .toList();
    }
}
