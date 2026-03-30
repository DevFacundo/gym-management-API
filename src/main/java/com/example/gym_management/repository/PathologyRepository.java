package com.example.gym_management.repository;

import com.example.gym_management.model.Pathology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PathologyRepository extends JpaRepository<Pathology, Long> {
    Optional<Pathology> findByNameIgnoreCase(String name);
    Set<Pathology> findAllByIdIn(List<Long> ids);
}
