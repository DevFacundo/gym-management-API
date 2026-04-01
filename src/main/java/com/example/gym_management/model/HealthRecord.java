package com.example.gym_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double height;
    private Double weight;

    @OneToOne
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Member member;

    @OneToMany(mappedBy = "healthRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("date DESC")
    @Builder.Default
    private List<Pathology> pathologies = new ArrayList<>();

    @OneToMany(mappedBy = "healthRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("date DESC")
    @Builder.Default
    private List<WeightRecord> weightRecords = new ArrayList<>();
}