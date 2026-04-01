package com.example.gym_management.service.implementations;

import com.example.gym_management.dto.request.MemberRequestDto;
import com.example.gym_management.dto.response.DebtResponseDto;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
        member.setEmail(dto.email());
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
    }

    private void updateClassSchedules(Member member, MemberRequestDto dto) {
        if (dto.classScheduleId() == null) return;

        Set<Long> newIds = new HashSet<>(dto.classScheduleId());

        // 1. Remover las que ya no están presentes en el DTO
        // Usamos un Iterator o removeIf para evitar ConcurrentModificationException
        member.getClassSchedules().removeIf(schedule -> {
            if (!newIds.contains(schedule.getId())) {
                schedule.getMembers().remove(member); // Sincronización manual inversa
                return true;
            }
            return false;
        });

        // 2. Agregar las nuevas
        Set<Long> currentIds = member.getClassSchedules().stream()
                .map(ClassSchedule::getId)
                .collect(Collectors.toSet());

        for (Long scheduleId : newIds) {
            if (!currentIds.contains(scheduleId)) {
                ClassSchedule schedule = classScheduleRepository.findById(scheduleId)
                        .orElseThrow(() -> new EntityNotFoundException("Clase no encontrada: " + scheduleId));

                // Validación de Regla de Negocio: Capacidad
                if (schedule.getMembers().size() >= schedule.getMaxCapacity()) {
                    throw new IllegalStateException("La clase de las " + schedule.getStartTime() + " está llena.");
                }

                member.addClassSchedule(schedule); // Uso del helper
            }
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
    @Transactional
    public List<MemberResponseDto> getAll() {
        return memberRepository.findAll().stream()
                .map(memberMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public List<MemberResponseDto> getAllActive() {
        return memberRepository.findByActiveTrue().stream()
                .map(memberMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public List<DebtResponseDto> getAllExpiredPayments() {
        List<Member> activeMembers = memberRepository.findAllByActiveTrue();

        return activeMembers.stream()
                .map(member -> {
                    if (member.getPayments() == null || member.getPayments().isEmpty()) {
                        return null;
                    }

                    return member.getPayments().stream()
                            .max(Comparator.comparing(Payment::getExpirationDate))
                            .map(lastPayment -> {
                                if (lastPayment.getExpirationDate().isBefore(LocalDate.now())) {

                                    long months = ChronoUnit.MONTHS.between(
                                            lastPayment.getExpirationDate().withDayOfMonth(1),
                                            LocalDate.now().withDayOfMonth(1)
                                    );
                                    if (months <= 0) months = 1;

                                    // AJUSTE SEGÚN TU RECORD:
                                    // 1. Long memberId
                                    // 2. String memberName
                                    // 3. Long monthsOwed (cambié int por long)
                                    // 4. BigDecimal totalDebtAmount
                                    // 5. LocalDate lastExpiration (la fecha que te faltaba)
                                    return new DebtResponseDto(
                                            member.getId(),
                                            member.getFirstName() + " " + member.getLastName(),
                                            months,
                                            lastPayment.getMembershipPlan().getPrice().multiply(BigDecimal.valueOf(months)),
                                            lastPayment.getExpirationDate()
                                    );
                                }
                                return null;
                            })
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .toList();
    }

//    @Override
//    public List<DebtResponseDto> getAllExpiredPayments() {
//        LocalDate today = LocalDate.now();
//        return memberRepository.findByActiveTrue().stream()
//                .map(member -> {
//                    LocalDate lastExpiry = member.getPayments().stream()
//                            .map(Payment::getExpirationDate)
//                            .max(LocalDate::compareTo)
//                            .orElse(member.getSignUpDate());
//
//                    long monthsOwed = java.time.temporal.ChronoUnit.MONTHS.between(
//                            lastExpiry.withDayOfMonth(1), today.withDayOfMonth(1));
//
//                    if (monthsOwed > 0) {
//                        BigDecimal planPrice = member.getPayments().isEmpty() ?
//                                BigDecimal.ZERO : member.getPayments().get(0).getMembershipPlan().getPrice();
//
//                        return new DebtResponseDto(
//                                member.getId(),
//                                member.getFirstName() + " " + member.getLastName(),
//                                monthsOwed,
//                                planPrice.multiply(BigDecimal.valueOf(monthsOwed)),
//                                lastExpiry
//                        );
//                    }
//                    return null;
//                })
//                .filter(Objects::nonNull)
//                .toList();
//    }
//    @Override
//    @Transactional
//    public List<DebtResponseDto> getAllExpiredPayments() {
//        // 1. Buscamos los pagos vencidos
//        List<Payment> expiredPayments = paymentRepository.findByExpirationDateBefore(LocalDate.now());
//
//        // 2. Obtenemos los IDs de los socios que deben
//        List<Long> memberIds = expiredPayments.stream()
//                .map(p -> p.getMember().getId())
//                .distinct()
//                .toList();
//
//        if (memberIds.isEmpty()) return List.of();
//
//
//        // Usamos el repositorio de Member para traerlos
//        return memberRepository.findAllByIdIn(memberIds).stream()
//                .map(memberMapper::toDto)
//                .toList();
//    }
//    @Override
//    @Transactional
//    public List<MemberResponseDto> getAllExpiredPayments() {
//        // 1. Buscamos los pagos vencidos
//        List<Payment> expiredPayments = paymentRepository.findByExpirationDateBefore(LocalDate.now());
//
//        // 2. Obtenemos los IDs de los socios que deben
//        List<Long> memberIds = expiredPayments.stream()
//                .map(p -> p.getMember().getId())
//                .distinct()
//                .toList();
//
//        if (memberIds.isEmpty()) return List.of();
//
//        // 3. ¡ESTA ES LA CLAVE!
//        // Usamos el repositorio de Member para traerlos
//        return memberRepository.findAllByIdIn(memberIds).stream()
//                .map(memberMapper::toDto)
//                .toList();
//    }


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
                        payment.getMember().getFirstName(),
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
