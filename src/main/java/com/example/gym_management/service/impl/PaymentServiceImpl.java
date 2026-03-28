package com.example.gym_management.service.impl;

import com.example.gym_management.dto.request.PaymentRequestDto;
import com.example.gym_management.dto.response.PaymentResponseDto;
import com.example.gym_management.exception.ResourceNotFoundException;
import com.example.gym_management.mapper.PaymentMapper;
import com.example.gym_management.model.Member;
import com.example.gym_management.model.Payment;
import com.example.gym_management.repository.MemberRepository;
import com.example.gym_management.repository.PaymentRepository;
import com.example.gym_management.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final MemberRepository memberRepository;

    @Override
    public PaymentResponseDto create(PaymentRequestDto paymentRequestDto) {
        Member member = memberRepository.findById(paymentRequestDto.memberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", paymentRequestDto.memberId()));

        Payment payment = Payment.builder()
                .amount(paymentRequestDto.amount())
                .paymentDate(paymentRequestDto.paymentDate())
                .member(member)
                .build();

        payment.setDefaultDates();

        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    @Transactional
    public PaymentResponseDto update(Long id, PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));

        payment.setAmount(paymentRequestDto.amount());
        if (paymentRequestDto.paymentDate() != null) {
            payment.setPaymentDate(paymentRequestDto.paymentDate());
            payment.setExpirationDate(paymentRequestDto.paymentDate().plusMonths(1));
        }

        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponseDto getById(Long id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
    }

    @Override
    public void delete(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
        paymentRepository.delete(payment);
    }

    @Override
    public List<PaymentResponseDto> getAllByMemberId(Long memberId) {
        return paymentRepository.findByMemberId(memberId)
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> getByPaymentDateBetween(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate)
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> getByExpirationDateBefore(LocalDate date) {
        return paymentRepository.findByExpirationDateBefore(date)
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public Double calculateEarningsForMonth(Integer year, Integer month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return paymentRepository.sumAmountByPaymentDateBetween(startDate, endDate)
                .orElse(0.0);
    }
}
