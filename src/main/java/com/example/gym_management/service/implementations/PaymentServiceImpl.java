package com.example.gym_management.service.implementations;

import com.example.gym_management.dto.request.PaymentRequestDto;
import com.example.gym_management.dto.response.PaymentResponseDto;
import com.example.gym_management.mapper.PaymentMapper;
import com.example.gym_management.model.Member;
import com.example.gym_management.model.MembershipPlan;
import com.example.gym_management.model.Payment;
import com.example.gym_management.repository.MemberRepository;
import com.example.gym_management.repository.MembershipPlanRepository;
import com.example.gym_management.repository.PaymentRepository;
import com.example.gym_management.service.interfaces.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final MemberRepository memberRepository;
    private final MembershipPlanRepository membershipPlanRepository;

    @Override
    public PaymentResponseDto create(PaymentRequestDto paymentRequestDto) {
        Member member = memberRepository.findById(paymentRequestDto.memberId())
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        MembershipPlan plan = membershipPlanRepository.findById(paymentRequestDto.membershipPlanId())
                .orElseThrow(() -> new EntityNotFoundException("MembershipPlan not found"));


        Payment payment = new Payment();
        payment.setMember(member);
        payment.setMembershipPlan(plan);
        payment.setAmount(paymentRequestDto.amount() != null ? paymentRequestDto.amount() : plan.getPrice());
        payment.setPaymentDate(paymentRequestDto.paymentDate() != null ? paymentRequestDto.paymentDate() : LocalDate.now());

        payment.setExpirationDate(payment.getPaymentDate().plusMonths(1));

        return paymentMapper.toDto(paymentRepository.save(payment));
    }


    @Override
    @Transactional
    public PaymentResponseDto update(Long id, PaymentRequestDto dto) {
        Payment entity = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        if (dto.memberId() != null) {
            Member member = memberRepository.findById(dto.memberId())
                    .orElseThrow(() -> new EntityNotFoundException("Member not found"));
            entity.setMember(member);
        }

        if (dto.membershipPlanId() != null) {
            MembershipPlan plan = membershipPlanRepository.findById(dto.membershipPlanId())
                    .orElseThrow(() -> new EntityNotFoundException("MembershipPlan not found"));
            entity.setMembershipPlan(plan);
            if (dto.amount() == null) {
                entity.setAmount(plan.getPrice());
            }
        }

        if (dto.amount() != null) entity.setAmount(dto.amount());
        if (dto.paymentDate() != null) {
            entity.setPaymentDate(dto.paymentDate());
            entity.setExpirationDate(entity.getPaymentDate().plusMonths(1));
        }

        return paymentMapper.toDto(paymentRepository.save(entity));
    }

    @Override
    public PaymentResponseDto getById(Long id) {
        Payment entity = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
        return paymentMapper.toDto(entity);
    }

    @Override
    public void delete(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("Payment not found");
        }
        paymentRepository.deleteById(id);
    }

    @Override
    public List<PaymentResponseDto> getAllByMemberId(Long memberId) {
        return paymentRepository.findByMemberId(memberId).stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> getByPaymentDateBetween(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate).stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> getByExpirationDateBefore(LocalDate endDate) {
        return paymentRepository.findByExpirationDateBefore(endDate).stream()
                .map(paymentMapper::toDto)
                .toList();
    }
    @Override
    public BigDecimal calculateEarningsForMonth(Integer year, Integer month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return paymentRepository.findByPaymentDateBetween(startDate,endDate)
                .stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
