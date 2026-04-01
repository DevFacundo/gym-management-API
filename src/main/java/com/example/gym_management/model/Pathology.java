package com.example.gym_management.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_pathology_health_record", columnList = "health_record_id"),
        @Index(name = "idx_pathology_active", columnList = "active")
})
public class Pathology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDate date;

    // false = desestimada (ej: osteopenia que evolucionó a osteoporosis)
    @Builder.Default
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_record_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HealthRecord healthRecord;
}
