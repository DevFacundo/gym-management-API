package com.example.gym_management.mapper;

import com.example.gym_management.dto.request.PaymentRequestDto;
import com.example.gym_management.dto.response.PaymentResponseDto;
import com.example.gym_management.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity (PaymentRequestDto requestDto);

    @Mapping(target = "memberName", expression = "java(payment.getMember().getFirstName() + \" \" + payment.getMember().getLastName())")
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "membershipPlanId", source = "membershipPlan.id")
    PaymentResponseDto toDto(Payment payment);
}
