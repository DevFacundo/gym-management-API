package com.example.gym_management.service.impl;

import com.example.gym_management.dto.request.PaymentRequestDto;
import com.example.gym_management.dto.response.PaymentResponseDto;
import com.example.gym_management.mapper.PaymentMapper;
import com.example.gym_management.model.Payment;
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

    @Override
    public PaymentResponseDto create(PaymentRequestDto paymentRequestDto) {
        return null;
    }

    @Override
    @Transactional
    public PaymentResponseDto update(Long id, PaymentRequestDto paymentRequestDto) {
        return null;
    }

    @Override
    public PaymentResponseDto getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<PaymentResponseDto> getAllByMemberId(Long memberId) {
        return List.of();
    }

    @Override
    public List<PaymentResponseDto> getByPaymentDateBetween(LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public List<PaymentResponseDto> getByExpirationDateBefore(LocalDate endDate) {
        return List.of();
    }

    @Override
    public Double calculateEarningsForMonth(Integer year, Integer month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return paymentRepository.findByPaymentDateBetween(startDate,endDate)
                .stream()
                .mapToDouble(Payment::getAmount)
                .sum();
    }
}
