package com.example.gym_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity ( name = "payments ")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
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
        if (this.amount == null && this.membershipPlan != null) {
            this.amount = this.membershipPlan.getPrice();
        }
    }

}
