package com.example.gym_management.service.interfaces;

import com.example.gym_management.dto.request.MemberRequestDto;
import com.example.gym_management.dto.response.DebtResponseDto;
import com.example.gym_management.dto.response.MemberResponseDto;
import com.example.gym_management.dto.response.PaymentResponseDto;

import java.util.List;

public interface MemberService {
    MemberResponseDto create(MemberRequestDto memberRequestDto);
    MemberResponseDto update(Long id, MemberRequestDto memberRequestDto);
    void delete(Long id);
    MemberResponseDto getById (Long id);
    MemberResponseDto getByFirstName(String firstName);
    MemberResponseDto getByFirstNameAndLastName(String firstName, String lastName);
    List<MemberResponseDto> getAll();
    List<MemberResponseDto> getAllActive();
    List<DebtResponseDto> getAllExpiredPayments();
    List<PaymentResponseDto> getAllPaymentsByMemberId(Long memberId);
    MemberResponseDto getByDni(String dni);
}
