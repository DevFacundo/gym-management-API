package com.example.gym_management.service;

import com.example.gym_management.dto.request.MemberRequestDto;
import com.example.gym_management.dto.response.MemberResponseDto;

import java.util.List;

public interface MemberService {
    MemberResponseDto create(MemberRequestDto memberRequestDto);
    MemberResponseDto update(Long id, MemberRequestDto memberRequestDto);
    void delete(Long id);
    MemberResponseDto geyById (Long id);
    MemberResponseDto getByFirstName(String firstName);
    MemberResponseDto getByFirstNameAndLastName(String firstName, String lastName);
    List<MemberResponseDto> getAll();
    List<MemberResponseDto> getAllActive();
}
