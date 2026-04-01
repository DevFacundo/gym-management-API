package com.example.gym_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(indexes = {
        @Index(name = "idx_member_firstname", columnList = "firstName"),
        @Index(name = "idx_member_lastname", columnList = "lastName"),
        @Index(name = "idx_member_active", columnList = "active")
})
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    private String dni;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String auxiliaryPhoneNumber;
    private Boolean active;
    private LocalDate birthDate;
    private LocalDate signUpDate;

    @OneToMany (mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private HealthRecord healthRecord;

    @ManyToMany
    @JoinTable(
            name = "member_class_schedules",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "class_schedule_id"),
            uniqueConstraints = @UniqueConstraint(
                    name = "UniqueMemberAndSchedule",
                    columnNames = {"member_id", "class_schedule_id"}
            )
    )
    private Set<ClassSchedule> classSchedules = new HashSet<>();

    @PrePersist
    public void onCreate() {
        this.active = true;
        if (this.signUpDate == null) {
            this.signUpDate = LocalDate.now();
        }
    }
    public void addClassSchedule(ClassSchedule schedule) {
        this.classSchedules.add(schedule);
        schedule.getMembers().add(this);
    }

    public void removeClassSchedule(ClassSchedule schedule) {
        this.classSchedules.remove(schedule);
        schedule.getMembers().remove(this);
    }

}
