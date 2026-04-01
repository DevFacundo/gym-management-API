package com.example.gym_management.service.implementations;

import com.example.gym_management.dto.request.WeightRecordRequestDto;
import com.example.gym_management.dto.response.WeightRecordResponseDto;
import com.example.gym_management.mapper.WeightRecordMapper;
import com.example.gym_management.model.HealthRecord;
import com.example.gym_management.model.WeightRecord;
import com.example.gym_management.repository.HealthRecordRepository;
import com.example.gym_management.repository.WeightRecordRepository;
import com.example.gym_management.service.interfaces.WeightRecordService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeightRecordServiceImpl implements WeightRecordService {

    private final WeightRecordRepository  weightRecordRepository;
    private final HealthRecordRepository  healthRecordRepository;
    private final WeightRecordMapper      weightRecordMapper;

    @Override
    @Transactional
    public WeightRecordResponseDto create(WeightRecordRequestDto dto) {
        HealthRecord healthRecord = healthRecordRepository.findById(dto.healthRecordId())
                .orElseThrow(() -> new EntityNotFoundException("HealthRecord no encontrado"));

        WeightRecord record = weightRecordMapper.toEntity(dto);
        record.setHealthRecord(healthRecord);

        // Si también actualizó la altura, reflejarlo en HealthRecord
        if (dto.height() != null) {
            healthRecord.setHeight(dto.height());
        }
        // Actualizar el peso "actual" en HealthRecord para acceso rápido
        healthRecord.setWeight(dto.weight());

        return weightRecordMapper.toDto(weightRecordRepository.save(record));
    }

    @Override
    @Transactional
    public WeightRecordResponseDto update(Long id, WeightRecordRequestDto dto) {
        WeightRecord entity = weightRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WeightRecord no encontrado"));

        entity.setWeight(dto.weight());
        if (dto.height() != null) entity.setHeight(dto.height());
        if (dto.date()   != null) entity.setDate(dto.date());
        if (dto.notes()  != null) entity.setNotes(dto.notes());

        return weightRecordMapper.toDto(weightRecordRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!weightRecordRepository.existsById(id)) {
            throw new EntityNotFoundException("WeightRecord no encontrado");
        }
        weightRecordRepository.deleteById(id);
    }

    @Override
    public List<WeightRecordResponseDto> getByHealthRecordId(Long healthRecordId) {
        return weightRecordRepository
                .findByHealthRecordIdOrderByDateDesc(healthRecordId)
                .stream().map(weightRecordMapper::toDto).toList();
    }
}
