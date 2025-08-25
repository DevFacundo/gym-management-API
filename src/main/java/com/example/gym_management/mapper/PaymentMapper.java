package com.example.gym_management.mapper;

import com.example.gym_management.dto.request.PaymentRequestDto;
import com.example.gym_management.dto.response.PaymentResponseDto;
import com.example.gym_management.model.Payment;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity (PaymentRequestDto requestDto);
    PaymentResponseDto toDto (Payment payment);
}
