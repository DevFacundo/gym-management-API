package com.example.gym_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payments", indexes = {
        @Index(name = "idx_payment_member", columnList = "member_id"),
        @Index(name = "idx_payment_expiration", columnList = "expirationDate"),
        @Index(name = "idx_payment_date", columnList = "paymentDate")
})
public class Payment {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private LocalDate paymentDate;

    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "membership_plan_id")
    private MembershipPlan membershipPlan;

    @PrePersist
    public void setDefaultDates() {
        if (this.paymentDate == null) {
            this.paymentDate = LocalDate.now();
        }
        if (this.expirationDate == null && this.member != null) {
            this.expirationDate = this.paymentDate.plusMonths(1);
        }
    }

}
