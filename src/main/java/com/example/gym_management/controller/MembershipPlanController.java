package com.example.gym_management.controller;

import com.example.gym_management.config.ApiPaths;
import com.example.gym_management.dto.request.MembershipPlanRequestDto;
import com.example.gym_management.dto.response.MembershipPlanResponseDto;
import com.example.gym_management.service.interfaces.MembershipPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.MEMBERSHIP_PLAN_BASE)
@RequiredArgsConstructor
public class MembershipPlanController {
    private final MembershipPlanService membershipPlanService;


    @GetMapping
    public ResponseEntity<List<MembershipPlanResponseDto>> getAll()
    {
        return ResponseEntity.ok(membershipPlanService.getAll());
    }

    @PostMapping
    public ResponseEntity<MembershipPlanResponseDto> create(
            @RequestBody MembershipPlanRequestDto membershipPlanRequestDto
    )
    {
        return ResponseEntity.ok(membershipPlanService.create(membershipPlanRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipPlanResponseDto> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(membershipPlanService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MembershipPlanResponseDto> delete(@PathVariable Long id) {
        membershipPlanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembershipPlanResponseDto> update(@PathVariable Long id,
                                                            @RequestBody MembershipPlanRequestDto dto) {
        return ResponseEntity.ok(membershipPlanService.update(id, dto));
    }

}
