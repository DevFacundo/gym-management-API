package com.example.gym_management.service.interfaces;

import com.example.gym_management.dto.request.ClassScheduleRequestDto;
import com.example.gym_management.dto.response.ClassScheduleResponseDto;
import com.example.gym_management.dto.response.MemberResponseDto;

import java.util.List;

public interface ClassScheduleService {
    ClassScheduleResponseDto create (ClassScheduleRequestDto classScheduleRequestDto);
    ClassScheduleResponseDto update (Long id, ClassScheduleRequestDto classScheduleRequestDto);
    List<ClassScheduleResponseDto> getAll();
    void delete (Long id);
    ClassScheduleResponseDto getById(Long id);
    List<MemberResponseDto> getAllMembersByClassScheduleId(Long classScheduleId);


}
