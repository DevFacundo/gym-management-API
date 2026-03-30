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

        // 1. Convertimos lo que viene del Front a un Set de IDs
        Set<Long> updatedIds = new HashSet<>(dto.classScheduleId());

        // 2. REGLA DE ORO: Solo borrar lo que ya no está en el DTO
        // Esto evita el "delete all" innecesario que confunde a Hibernate
        member.getClassSchedules().removeIf(existingSchedule -> {
            if (!updatedIds.contains(existingSchedule.getId())) {
                existingSchedule.getMembers().remove(member); // Limpieza bidireccional
                return true;
            }
            return false;
        });

        // 3. REGLA DE ORO: Solo agregar lo que el socio NO tenía ya
        Set<Long> currentlyAssignedIds = member.getClassSchedules().stream()
                .map(ClassSchedule::getId)
                .collect(Collectors.toSet());

        for (Long newId : updatedIds) {
            if (!currentlyAssignedIds.contains(newId)) {
                classScheduleRepository.findById(newId).ifPresent(schedule -> {
                    if (schedule.getMembers().size() < schedule.getMaxCapacity()) {
                        member.getClassSchedules().add(schedule);
                        schedule.getMembers().add(member);
                    } else {
                        throw new IllegalStateException("La clase de las " + schedule.getStartTime() + " está llena.");
                    }
                });
            }
        }
    }

//    private void updateClassSchedules(Member member, MemberRequestDto dto) {
//        if (dto.classScheduleId() == null) return;
//
//        // 1. LIMPIEZA BIDIRECCIONAL (Fundamental para evitar los clones)
//        // Antes de limpiar al socio, le avisamos a cada clase que el socio se va
//        for (ClassSchedule currentSchedule : member.getClassSchedules()) {
//            currentSchedule.getMembers().remove(member);
//        }
//
//        // 2. Ahora sí limpiamos la lista del socio
//        member.getClassSchedules().clear();
//
//        // 3. Agregamos las clases nuevas (o las que quedaron tildadas)
//        for (Long scheduleId : dto.classScheduleId()) {
//            classScheduleRepository.findById(scheduleId).ifPresent(schedule -> {
//                // Sincronizamos ambos lados
//                if (!member.getClassSchedules().contains(schedule)) {
//                    if (schedule.getMembers().size() < schedule.getMaxCapacity()) {
//                        member.getClassSchedules().add(schedule);
//                        schedule.getMembers().add(member); // <--- Esto mantiene la DB sana
//                    } else {
//                        // Si ya estaba (update), no lanzamos error de capacidad
//                        if (!schedule.getMembers().contains(member)) {
//                            throw new IllegalStateException("Clase llena: " + schedule.getId());
//                        }
//                    }
//                }
//            });
//        }
//    }

//    private void updateClassSchedules(Member member, MemberRequestDto dto) {
//        if (dto.classScheduleId() == null) return;
//
//        // 1. ANTES DE LIMPIAR: Avisar a las clases actuales que esta alumna se va
//        for (ClassSchedule currentSchedule : member.getClassSchedules()) {
//            currentSchedule.getMembers().remove(member);
//        }
//
//        // 2. Ahora sí limpiamos la lista de la alumna
//        member.getClassSchedules().clear();
//
//        // 3. Agregamos las nuevas (o ninguna, si destildaste todo)
//        for (Long scheduleId : dto.classScheduleId()) {
//            classScheduleRepository.findById(scheduleId).ifPresent(schedule -> {
//                // Validar capacidad
//                if (schedule.getMembers().size() < schedule.getMaxCapacity()) {
//                    member.getClassSchedules().add(schedule);
//                    schedule.getMembers().add(member); // Sincronización bidireccional
//                } else {
//                    // Si ya estaba en la clase (por ejemplo en un update parcial), no lanzamos error
//                    if (!schedule.getMembers().contains(member)) {
//                        throw new IllegalStateException("La clase " + schedule.getId() + " está llena.");
//                    }
//                }
//            });
//        }
//    }

//    private void updateClassSchedules(Member member, MemberRequestDto dto) {
//        if (dto.classScheduleId() == null) return;
//
//
//        member.getClassSchedules().clear();
//
//        for (Long scheduleId : dto.classScheduleId()) {
//            classScheduleRepository.findById(scheduleId).ifPresent(schedule -> {
//                // not duplicated
//                if (!member.getClassSchedules().contains(schedule)) {
//                    // Valiate capacity
//                    if (schedule.getMembers().size() < schedule.getMaxCapacity()) {
//                        member.getClassSchedules().add(schedule);
//                        schedule.getMembers().add(member);
//                    } else {
//                        throw new IllegalStateException("ClassSchedule is full: " + schedule.getId());
//                    }
//                }
//            });
//        }
//    }
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
