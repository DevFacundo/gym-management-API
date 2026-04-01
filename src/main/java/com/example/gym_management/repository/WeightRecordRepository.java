package com.example.gym_management.repository;

import com.example.gym_management.model.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {

    List<WeightRecord> findByHealthRecordIdOrderByDateDesc(Long healthRecordId);

    // El más reciente — para calcular IMC actual
    Optional<WeightRecord> findFirstByHealthRecordIdOrderByDateDesc(Long healthRecordId);
}
