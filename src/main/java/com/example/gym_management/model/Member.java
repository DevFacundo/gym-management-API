package com.example.gym_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String dni;
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

    @ManyToMany(mappedBy = "members")
    private List<ClassSchedule> classSchedules = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        this.active = true;
        if (this.signUpDate == null) {
            this.signUpDate = LocalDate.now();
        }
    }
}
