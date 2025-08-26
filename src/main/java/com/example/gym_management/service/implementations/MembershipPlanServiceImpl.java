package com.example.gym_management.service.implementations;

import com.example.gym_management.dto.request.MembershipPlanRequestDto;
import com.example.gym_management.dto.response.MembershipPlanResponseDto;
import com.example.gym_management.mapper.MembershipPlanMapper;
import com.example.gym_management.model.MembershipPlan;
import com.example.gym_management.repository.MembershipPlanRepository;
import com.example.gym_management.service.interfaces.MembershipPlanService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipPlanServiceImpl implements MembershipPlanService {

    private final MembershipPlanRepository membershipPlanRepository;
    private final MembershipPlanMapper membershipPlanMapper;

    @Override
    public MembershipPlanResponseDto create(MembershipPlanRequestDto dto) {
        MembershipPlan plan = membershipPlanMapper.toEntity(dto);
        return membershipPlanMapper.toDto(membershipPlanRepository.save(plan));
    }
    @Override
    public MembershipPlanResponseDto update(Long id, MembershipPlanRequestDto dto) {
        MembershipPlan plan = membershipPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MembershipPlan not found"));

        plan.setPlanName(dto.planName());
        plan.setPrice(dto.price());
        return membershipPlanMapper.toDto(membershipPlanRepository.save(plan));
    }

    @Override
    public void delete(Long id) {
        if (!membershipPlanRepository.existsById(id)) {
            throw new EntityNotFoundException("MembershipPlan not found");
        }
        membershipPlanRepository.deleteById(id);
    }

    @Override
    public MembershipPlanResponseDto getById(Long id) {
        MembershipPlan plan = membershipPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MembershipPlan not found"));
        return membershipPlanMapper.toDto(plan);
    }

    @Override
    public List<MembershipPlanResponseDto> getAll() {
        return membershipPlanRepository.findAll()
                .stream()
                .map(membershipPlanMapper::toDto)
                .toList();
    }

}
