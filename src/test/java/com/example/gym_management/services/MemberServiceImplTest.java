package com.example.gym_management.services;

import com.example.gym_management.dto.request.MemberRequestDto;
import com.example.gym_management.dto.response.MemberResponseDto;
import com.example.gym_management.mapper.MemberMapper;
import com.example.gym_management.model.Member;
import com.example.gym_management.repository.MemberRepository;
import com.example.gym_management.repository.PaymentRepository;
import com.example.gym_management.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberServiceImpl memberService;

    private Member member;
    private MemberResponseDto memberResponseDto;
    private MemberRequestDto memberRequestDto;

    @BeforeEach
    void setUp(){
        member = Member.builder()
                .id(1L)
                .firstName("Facundo")
                .lastName("Aguilera")
                .phoneNumber("2235829879")
                .active(true)
                .build();
    }


}
