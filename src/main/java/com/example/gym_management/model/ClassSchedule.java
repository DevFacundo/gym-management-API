package com.example.gym_management.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    private LocalTime startTime;
    private LocalTime endTime;

    @Builder.Default
    private int maxCapacity = 6;

    @Builder.Default
    @ManyToMany(mappedBy = "classSchedules")
    @JsonIgnoreProperties("classSchedules")
    private Set<Member> members = new HashSet<>();
}