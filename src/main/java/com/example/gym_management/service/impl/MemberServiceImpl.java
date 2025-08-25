package com.example.gym_management.service.impl;

import com.example.gym_management.dto.request.MemberRequestDto;
import com.example.gym_management.dto.response.MemberResponseDto;
import com.example.gym_management.mapper.MemberMapper;
import com.example.gym_management.model.Member;
import com.example.gym_management.repository.MemberRepository;
import com.example.gym_management.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;


    @Override
    public MemberResponseDto create(MemberRequestDto memberRequestDto) {
        Member member = memberMapper.toEntity(memberRequestDto);



        return memberMapper.toDto(memberRepository.save(member));
    }

    @Override
    @Transactional
    public MemberResponseDto update(Long id, MemberRequestDto memberRequestDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public MemberResponseDto geyById(Long id) {
        return null;
    }

    @Override
    public MemberResponseDto getByFirstName(String firstName) {
        return null;
    }

    @Override
    public MemberResponseDto getByFirstNameAndLastName(String firstName, String lastName) {
        return null;
    }

    @Override
    public List<MemberResponseDto> getAll() {
        return List.of();
    }

    @Override
    public List<MemberResponseDto> getAllActive() {
        return List.of();
    }
}
