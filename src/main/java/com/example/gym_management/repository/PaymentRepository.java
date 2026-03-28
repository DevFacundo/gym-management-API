package com.example.gym_management.repository;

import com.example.gym_management.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByMemberId(Long memberId);
    List<Payment> findByPaymentDateBetween (LocalDate start, LocalDate end);
    List<Payment> findByExpirationDateBefore (LocalDate date);

    @Query("SELECT p FROM Payment p JOIN p.member m WHERE p.expirationDate < :date AND m.active = true")
    List<Payment> findExpiredPaymentsOfActiveMembers(@Param("date") LocalDate date);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.paymentDate BETWEEN :start AND :end")
    Optional<Double> sumAmountByPaymentDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

}
