package com.example.gym_management.dto.response;

import java.time.LocalDate;
import java.util.List;

public record MemberResponseDto(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        String auxiliaryPhoneNumber,
        Boolean active,
        LocalDate birthDate,
        LocalDate signUpDate,
        HealthRecordResponseDto healthRecord,
        List<ClassScheduleResponseDto> classSchedules
) {
}
