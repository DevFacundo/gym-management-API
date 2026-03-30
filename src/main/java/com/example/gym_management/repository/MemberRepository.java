package com.example.gym_management.repository;

import com.example.gym_management.model.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByFirstName(String firstName);
    Optional<Member> findByFirstNameAndLastName(String firstName, String lastName);
    List<Member> findByActiveTrue();
    Optional<Member> findByDni(String dni);

    @Override
    @EntityGraph(attributePaths = {
            "healthRecord",
            "healthRecord.pathologies",
            "classSchedules",
            "classSchedules.members",
    })
    List<Member>findAll();

    @EntityGraph(attributePaths = {
            "classSchedules",
            "classSchedules.members",
            "healthRecord",
            "healthRecord.pathologies"
    })
    List<Member> findAllByIdIn(List<Long> ids);

    @EntityGraph(attributePaths = {"payments", "payments.membershipPlan"})// Traemos pagos y el plan para el precio
    List<Member> findAllByActiveTrue();
}
