package com.example.gym_management.mapper;

import com.example.gym_management.dto.request.MemberRequestDto;
import com.example.gym_management.dto.response.MemberResponseDto;
import com.example.gym_management.model.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {HealthRecordMapper.class, ClassScheduleMapper.class})
public interface MemberMapper {
    Member toEntity (MemberRequestDto memberRequestDto);
    MemberResponseDto toDto (Member member);
}
