package com.example.gym_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String auxiliaryPhoneNumber;
    private Double height;
    private Double weight;
    private Boolean active;
    private LocalDate birthDate;
    private LocalDate signUpDate;

    @OneToMany (mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "member_pathologies",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn (name = "pathology_id")
    )
    private List<Pathology> pathologies = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        this.active = true;
        if (this.signUpDate == null) {
            this.signUpDate = LocalDate.now();
        }
    }
}
