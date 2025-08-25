package com.example.gym_management.repository;

import com.example.gym_management.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByMemberId(Long memberId);
    List<Payment> findByPaymentDateBetween (LocalDate start, LocalDate end);
    List<Payment> findByExpirationDateBefore (LocalDate date);
}
