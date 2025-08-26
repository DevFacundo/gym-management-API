package com.example.gym_management.repository;

import com.example.gym_management.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByFirstName(String firstName);
    Optional<Member> findByFirstNameAndLastName(String firstName, String lastName);
    List<Member> findByActiveTrue();
}
