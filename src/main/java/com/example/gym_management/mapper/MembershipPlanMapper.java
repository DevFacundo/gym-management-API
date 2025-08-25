package com.example.gym_management.mapper;

import com.example.gym_management.dto.request.MembershipPlanRequestDto;
import com.example.gym_management.dto.response.MembershipPlanResponseDto;
import com.example.gym_management.model.MembershipPlan;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface MembershipPlanMapper {
    MembershipPlan toEntity (MembershipPlanRequestDto membershipPlanRequestDto);
    MembershipPlanResponseDto toDto (MembershipPlan membershipPlan);
}
