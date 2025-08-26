package com.example.gym_management.service.interfaces;

import com.example.gym_management.dto.request.MembershipPlanRequestDto;
import com.example.gym_management.dto.response.MembershipPlanResponseDto;

import java.util.List;

public interface MembershipPlanService {
    MembershipPlanResponseDto create (MembershipPlanRequestDto membershipPlanRequestDto);
    MembershipPlanResponseDto update (Long id, MembershipPlanRequestDto membershipPlanRequestDto);
    void delete(Long id);
    MembershipPlanResponseDto getById(Long id);
    List<MembershipPlanResponseDto> getAll();
}
