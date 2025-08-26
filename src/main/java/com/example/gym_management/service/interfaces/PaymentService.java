package com.example.gym_management.service.interfaces;

import com.example.gym_management.dto.request.PaymentRequestDto;
import com.example.gym_management.dto.response.PaymentResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    PaymentResponseDto create(PaymentRequestDto paymentRequestDto);
    PaymentResponseDto update(Long id, PaymentRequestDto paymentRequestDto);
    PaymentResponseDto getById(Long id);
    void delete(Long id);
    List<PaymentResponseDto> getAllByMemberId(Long memberId);
    List<PaymentResponseDto> getByPaymentDateBetween(LocalDate startDate, LocalDate endDate);
    List<PaymentResponseDto> getByExpirationDateBefore(LocalDate endDate);
    BigDecimal calculateEarningsForMonth(Integer year, Integer month);

}
