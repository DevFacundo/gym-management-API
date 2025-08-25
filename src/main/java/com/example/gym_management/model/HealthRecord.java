package com.example.gym_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "health_records")
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
    private Member member;

    @ManyToMany
    @JoinTable(
            name = "healthrecord_pathologies",
            joinColumns = @JoinColumn(name = "health_record_id"),
            inverseJoinColumns = @JoinColumn(name = "pathology_id")
    )
    private List<Pathology> pathologies = new ArrayList<>();
}