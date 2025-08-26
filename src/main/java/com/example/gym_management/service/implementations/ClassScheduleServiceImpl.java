package com.example.gym_management.service.implementations;

import com.example.gym_management.dto.request.ClassScheduleRequestDto;
import com.example.gym_management.dto.response.ClassScheduleResponseDto;
import com.example.gym_management.dto.response.MemberResponseDto;
import com.example.gym_management.mapper.ClassScheduleMapper;
import com.example.gym_management.mapper.MemberMapper;
import com.example.gym_management.model.ClassSchedule;
import com.example.gym_management.repository.ClassScheduleRepository;
import com.example.gym_management.service.interfaces.ClassScheduleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassScheduleServiceImpl implements ClassScheduleService {

    private final ClassScheduleRepository classScheduleRepository;
    private final ClassScheduleMapper classScheduleMapper;
    private final MemberMapper memberMapper;

    @Override
    public ClassScheduleResponseDto create(ClassScheduleRequestDto dto) {
        ClassSchedule classSchedule = classScheduleMapper.toEntity(dto);
        return classScheduleMapper.toDto(classScheduleRepository.save(classSchedule));
    }

    @Override
    @Transactional
    public ClassScheduleResponseDto update(Long id, ClassScheduleRequestDto dto) {
        ClassSchedule entity = classScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ClassSchedule not found"));


        if (dto.day() != null) entity.setDay(dto.day());
        if (dto.startTime() != null) entity.setStartTime(dto.startTime());
        if (dto.endTime() != null) entity.setEndTime(dto.endTime());
        if (dto.maxCapacity() != null) entity.setMaxCapacity(dto.maxCapacity());

        return classScheduleMapper.toDto(classScheduleRepository.save(entity));
    }

    @Override
    @Transactional
    public List<ClassScheduleResponseDto> getAll() {
        return classScheduleRepository.findAll()
                .stream()
                .map(classScheduleMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!classScheduleRepository.existsById(id)) {
            throw new EntityNotFoundException("ClassSchedule not found");
        }
        classScheduleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ClassScheduleResponseDto getById(Long id) {
        ClassSchedule entity = classScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ClassSchedule not found"));
        return classScheduleMapper.toDto(entity);
    }

    @Override
    @Transactional
    public List<MemberResponseDto> getAllMembersByClassScheduleId(Long classScheduleId) {
        ClassSchedule classSchedule = classScheduleRepository.findById(classScheduleId)
                .orElseThrow(() -> new EntityNotFoundException("ClassSchedule not found"));
        return classSchedule.getMembers().stream()
                .map(memberMapper::toDto)
                .toList();
    }

}
