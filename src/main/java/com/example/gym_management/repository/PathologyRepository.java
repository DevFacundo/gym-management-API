package com.example.gym_management.repository;

import com.example.gym_management.model.Pathology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PathologyRepository extends JpaRepository<Pathology, Long> {
    Optional<Pathology> findByNameIgnoreCase(String name);
}
