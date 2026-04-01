package com.example.gym_management.repository;

import com.example.gym_management.model.Pathology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PathologyRepository extends JpaRepository<Pathology, Long> {

    List<Pathology> findByHealthRecordIdOrderByDateDesc(Long healthRecordId);
    List<Pathology> findByHealthRecordIdAndActiveOrderByDateDesc(Long healthRecordId, Boolean active);
}
