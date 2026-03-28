package com.example.gym_management.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

@Test
    void setDefaultDates_ifNotPaymentDateValue_setLocalDateNow(){
    Payment payment = new Payment();
    payment.setDefaultDates();
    assertEquals(LocalDate.now(), payment.getPaymentDate());
}

    @Test
    void onCreate_ifNotDateValue_OtherLocalDateNow(){
        Payment payment = new Payment();
        LocalDate otherDate = LocalDate.of(2025,1,25);
        payment.setPaymentDate(otherDate);
        payment.setDefaultDates();
        assertEquals(otherDate, payment.getPaymentDate());
    }

    @Test
    void onCreate_ifNotExpirationDateValue_PlusAMonth(){
        Payment payment = new Payment();
        payment.setMember(new Member());
        payment.setDefaultDates();
        assertEquals(LocalDate.now().plusMonths(1), payment.getExpirationDate());
    }
    @Test
    void setDefaultDates_whenMemberIsNull_expirationDateIsNull(){
    Payment payment = new Payment();
    payment.setDefaultDates();
    assertNull(payment.getExpirationDate());
    }

}
