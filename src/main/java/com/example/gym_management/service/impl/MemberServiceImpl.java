package com.example.gym_management.service.impl;

import com.example.gym_management.dto.request.MemberRequestDto;
import com.example.gym_management.dto.response.MemberResponseDto;
import com.example.gym_management.exception.ResourceNotFoundException;
import com.example.gym_management.mapper.MemberMapper;
import com.example.gym_management.model.Member;
import com.example.gym_management.model.Payment;
import com.example.gym_management.repository.MemberRepository;
import com.example.gym_management.repository.PaymentRepository;
import com.example.gym_management.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PaymentRepository paymentRepository;

    @Override
    public MemberResponseDto create(MemberRequestDto memberRequestDto) {
        Member member = memberMapper.toEntity(memberRequestDto);
        return memberMapper.toDto(memberRepository.save(member));
    }

    @Override
    @Transactional
    public MemberResponseDto update(Long id, MemberRequestDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));

        member.setFirstName(dto.firstName());
        member.setLastName(dto.lastName());
        member.setPhoneNumber(dto.phoneNumber());
        member.setAuxiliaryPhoneNumber(dto.auxiliaryPhoneNumber());
        member.setBirthDate(dto.birthDate());

        return memberMapper.toDto(memberRepository.save(member));
    }

    @Override
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));
        memberRepository.delete(member);
    }

    @Override
    public MemberResponseDto geyById(Long id) {
        return memberRepository.findById(id)
                .map(memberMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));
    }

    @Override
    public MemberResponseDto getByFirstName(String firstName) {
        return memberRepository.findByFirstName(firstName)
                .map(memberMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Member con nombre "+ firstName + " no encontrado"));
    }

    @Override
    public MemberResponseDto getByFirstNameAndLastName(String firstName, String lastName) {
        return  memberRepository.findByFirstNameAndLastName(firstName, lastName)
                .map(memberMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Member con nombre "+ firstName + "y apellido " +lastName + " no encontrado") );
    }

    @Override
    public List<MemberResponseDto> getAll() {
        return memberRepository.findAll()
                .stream()
                .map(memberMapper::toDto)
                .toList();
    }

    @Override
    public List<MemberResponseDto> getAllActive() {
        return memberRepository.findByActive(true)
                .stream()
                .map(memberMapper::toDto)
                .toList();
    }

    @Override
    public List<MemberResponseDto> getAllExpiredPayments() {
        return paymentRepository.findExpiredPaymentsOfActiveMembers(LocalDate.now())
                .stream()
                .map(Payment::getMember)
                .distinct()
                .map(memberMapper::toDto)
                .toList();
    }
}
