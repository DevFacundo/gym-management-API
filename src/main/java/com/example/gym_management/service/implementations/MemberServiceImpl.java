package com.example.gym_management.service.implementations;

import com.example.gym_management.dto.request.MemberRequestDto;
import com.example.gym_management.dto.response.MemberResponseDto;
import com.example.gym_management.dto.response.PaymentResponseDto;
import com.example.gym_management.mapper.MemberMapper;
import com.example.gym_management.model.Member;
import com.example.gym_management.model.Payment;
import com.example.gym_management.repository.ClassScheduleRepository;
import com.example.gym_management.repository.MemberRepository;
import com.example.gym_management.repository.PaymentRepository;
import com.example.gym_management.service.interfaces.MemberService;
import jakarta.persistence.EntityNotFoundException;
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
    private final ClassScheduleRepository classScheduleRepository;

    @Override
    public MemberResponseDto create(MemberRequestDto dto) {
        Member member = memberMapper.toEntity(dto);
        return memberMapper.toDto(memberRepository.save(member));
    }

    @Override
    @Transactional
    public MemberResponseDto update(Long id, MemberRequestDto dto) {
        Member existing = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        existing.setFirstName(dto.firstName());
        existing.setLastName(dto.lastName());
        existing.setPhoneNumber(dto.phoneNumber());
        existing.setAuxiliaryPhoneNumber(dto.auxiliaryPhoneNumber());
        existing.setBirthDate(dto.birthDate());
        return memberMapper.toDto(memberRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public MemberResponseDto getById(Long id) {
        return memberRepository.findById(id)
                .map(memberMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Override
    public MemberResponseDto getByFirstName(String firstName) {
        return memberRepository.findByFirstName(firstName)
                .map(memberMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Override
    public MemberResponseDto getByFirstNameAndLastName(String firstName, String lastName) {
        return memberRepository.findByFirstNameAndLastName(firstName, lastName)
                .map(memberMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Override
    public List<MemberResponseDto> getAll() {
        return memberRepository.findAll().stream()
                .map(memberMapper::toDto)
                .toList();
    }

    @Override
    public List<MemberResponseDto> getAllActive() {
        return memberRepository.findByActiveTrue().stream()
                .map(memberMapper::toDto)
                .toList();
    }

    @Override
    public List<MemberResponseDto> getAllExpiredPayments() {
        List<Payment> expiredPayments = paymentRepository.findByExpirationDateBefore(LocalDate.now());
        return expiredPayments.stream()
                .map(Payment::getMember)
                .distinct()
                .map(memberMapper::toDto)
                .toList();
    }


    @Override
    public List<PaymentResponseDto> getAllPaymentsByMemberId(Long memberId) {
        List<Payment> payments = paymentRepository.findByMemberId(memberId);

        return payments.stream()
                .map(payment -> new PaymentResponseDto(
                        payment.getId(),
                        payment.getAmount(),
                        payment.getPaymentDate(),
                        payment.getExpirationDate(),
                        payment.getMember().getId(),
                        payment.getMembershipPlan().getId()
                ))
                .toList();
    }
}
