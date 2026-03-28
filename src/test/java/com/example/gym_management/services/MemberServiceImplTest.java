package com.example.gym_management.services;

import com.example.gym_management.dto.request.MemberRequestDto;
import com.example.gym_management.dto.response.MemberResponseDto;
import com.example.gym_management.mapper.MemberMapper;
import com.example.gym_management.model.Member;
import com.example.gym_management.repository.ClassScheduleRepository;
import com.example.gym_management.repository.MemberRepository;
import com.example.gym_management.repository.PathologyRepository;
import com.example.gym_management.repository.PaymentRepository;
import com.example.gym_management.service.implementations.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @Mock private MemberRepository memberRepository;
    @Mock private PaymentRepository paymentRepository;
    @Mock private ClassScheduleRepository classScheduleRepository;
    @Mock private PathologyRepository pathologyRepository;
    @Mock private MemberMapper memberMapper;


    @InjectMocks
    private MemberServiceImpl memberService;

    private Member member;
    private MemberResponseDto memberResponseDto;
    private MemberRequestDto memberRequestDto;

    @BeforeEach
  /*  void setUp(){
        member = Member.builder()
                .id(1L)
                .dni("12345678")
                .firstName("Ana")
                .lastName("Garcia")
                .phoneNumber("223456789")
                .active(true)
                .classSchedules(new ArrayList<>())
                .payments(new ArrayList<>())
                .build();

        memberResponseDto = new MemberResponseDto(
                1L, "12345678", "Ana", "García",
                "223456789", null, true,
                null, null, null, List.of()
        );
        memberRequestDto = new MemberRequestDto(
                "Ana", "García", "223456789",
                "12345678", null, null, null, null, null
        );
    }*/

        //CREATE

        @Test
        void create_shouldSaveAndReturnDto(){
            when(memberMapper.toEntity(memberRequestDto)).thenReturn(member);
            when(memberMapper.toDto(member)).thenReturn(memberResponseDto);
            when(memberRepository.save(member)).thenReturn(member);

            MemberResponseDto result = memberService.create(memberRequestDto);

            assertEquals(memberResponseDto, result);
            verify(memberRepository).save(member);
        }

        // --- getById ---

        @Test
        void getMemberById_shouldReturnMember(){

        }



}
