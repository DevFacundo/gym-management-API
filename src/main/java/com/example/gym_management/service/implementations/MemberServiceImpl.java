package com.example.gym_management.service.implementations;

import com.example.gym_management.dto.request.MemberRequestDto;
import com.example.gym_management.dto.response.MemberResponseDto;
import com.example.gym_management.dto.response.PaymentResponseDto;
import com.example.gym_management.mapper.MemberMapper;
import com.example.gym_management.model.*;
import com.example.gym_management.repository.ClassScheduleRepository;
import com.example.gym_management.repository.MemberRepository;
import com.example.gym_management.repository.PathologyRepository;
import com.example.gym_management.repository.PaymentRepository;
import com.example.gym_management.service.interfaces.MemberService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PaymentRepository paymentRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final PathologyRepository pathologyRepository;

    @Override
    public MemberResponseDto create(MemberRequestDto dto) {
        Member member = memberMapper.toEntity(dto);
        return memberMapper.toDto(memberRepository.save(member));
    }

    @Override
    @Transactional
    public MemberResponseDto update(Long id, MemberRequestDto dto) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        updateBasicInfo(existingMember, dto);
        updateHealthRecord(existingMember, dto);
        updateClassSchedules(existingMember, dto);

        return memberMapper.toDto(memberRepository.save(existingMember));
    }

    private void updateBasicInfo(Member member, MemberRequestDto dto) {
        member.setFirstName(dto.firstName());
        member.setLastName(dto.lastName());
        member.setPhoneNumber(dto.phoneNumber());
        member.setAuxiliaryPhoneNumber(dto.auxiliaryPhoneNumber());
        member.setBirthDate(dto.birthDate());
    }

    private void updateHealthRecord(Member member, MemberRequestDto dto) {
        if (dto.healthRecord() == null) return;

        HealthRecord healthRecord = member.getHealthRecord();
        if (healthRecord == null) {
            healthRecord = new HealthRecord();
            healthRecord.setMember(member);
            member.setHealthRecord(healthRecord);
        }

        healthRecord.setHeight(dto.healthRecord().height());
        healthRecord.setWeight(dto.healthRecord().weight());

        if (dto.healthRecord().pathologyId() != null) {
            healthRecord.getPathologies().clear();

            for (Long pathologyId : dto.healthRecord().pathologyId()) {
                Optional<Pathology> optional = pathologyRepository.findById(pathologyId);
                if (optional.isPresent()) {
                    healthRecord.getPathologies().add(optional.get());
                }
            }
        }
    }

    private void updateClassSchedules(Member member, MemberRequestDto dto) {
        if (dto.classScheduleId() == null) return;


        member.getClassSchedules().clear();

        for (Long scheduleId : dto.classScheduleId()) {
            classScheduleRepository.findById(scheduleId).ifPresent(schedule -> {
                // not duplicated
                if (!member.getClassSchedules().contains(schedule)) {
                    // Valiate capacity
                    if (schedule.getMembers().size() < schedule.getMaxCapacity()) {
                        member.getClassSchedules().add(schedule);
                        schedule.getMembers().add(member);
                    } else {
                        throw new IllegalStateException("ClassSchedule is full: " + schedule.getId());
                    }
                }
            });
        }
    }
    @Override
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MemberResponseDto getById(Long id) {
        return memberRepository.findById(id)
                .map(memberMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Override
    public MemberResponseDto getByFirstName(String firstName) {
        return memberRepository.findByFirstName(firstName)
                .map(memberMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Override
    public MemberResponseDto getByFirstNameAndLastName(String firstName, String lastName) {
        return memberRepository.findByFirstNameAndLastName(firstName, lastName)
                .map(memberMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Override
    public List<MemberResponseDto> getAll() {
        return memberRepository.findAll().stream()
                .map(memberMapper::toDto)
                .toList();
    }

    @Override
    public List<MemberResponseDto> getAllActive() {
        return memberRepository.findByActiveTrue().stream()
                .map(memberMapper::toDto)
                .toList();
    }

    @Override
    public List<MemberResponseDto> getAllExpiredPayments() {
        List<Payment> expiredPayments = paymentRepository.findByExpirationDateBefore(LocalDate.now());
        return expiredPayments.stream()
                .map(Payment::getMember)
                .distinct()
                .map(memberMapper::toDto)
                .toList();
    }


    @Override
    public List<PaymentResponseDto> getAllPaymentsByMemberId(Long memberId) {
        List<Payment> payments = paymentRepository.findByMemberId(memberId);

        return payments.stream()
                .map(payment -> new PaymentResponseDto(
                        payment.getId(),
                        payment.getAmount(),
                        payment.getPaymentDate(),
                        payment.getExpirationDate(),
                        payment.getMember().getId(),
                        payment.getMembershipPlan().getId()
                ))
                .toList();
    }

    @Override
    public MemberResponseDto getByDni(String dni) {
        return memberRepository.findByDni(dni)
                .map(memberMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }
}
