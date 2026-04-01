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
        @Index(name = "idx_weight_record_health_record", columnList = "health_record_id"),
        @Index(name = "idx_weight_record_date", columnList = "date")
})
public class WeightRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double weight;

    // La altura puede cambiar en alumnos jóvenes o post-cirugía
    private Double height;

    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String notes; // "bajó 2kg desde que empezó a venir 3 veces", etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_record_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HealthRecord healthRecord;

    @PrePersist
    public void setDefaultDate() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }
}