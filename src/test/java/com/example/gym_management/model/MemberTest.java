package com.example.gym_management.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {

    @Test
    void onCreate_itShouldSetActiveTrue(){
        Member member = new Member();
        member.onCreate();
        assertTrue(member.getActive());
    }

    @Test
    void onCreate_whenSignUpDateIsNull_itShouldSetNowDate(){
        Member member = new Member();
        member.onCreate();
        assertEquals(LocalDate.now(), member.getSignUpDate());
    }

    @Test
    void onCreate_whenSignUpDateHasValue_itShouldNotModifyDate(){
        Member member = new Member();
        LocalDate otherDate = LocalDate.of(2025,1,25);
        member.setSignUpDate(otherDate);
        member.onCreate();
        assertEquals(otherDate, member.getSignUpDate());
    }
}
