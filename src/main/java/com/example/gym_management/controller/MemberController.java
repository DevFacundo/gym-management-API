package com.example.gym_management.controller;

import com.example.gym_management.config.ApiPaths;
import com.example.gym_management.dto.request.MemberRequestDto;
import com.example.gym_management.dto.response.MemberResponseDto;
import com.example.gym_management.dto.response.PaymentResponseDto;
import com.example.gym_management.service.interfaces.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.MEMBER_BASE)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDto> create (@RequestBody
                                                     @Valid MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.create(memberRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> update (@PathVariable Long id,
                                                     @RequestBody @Valid MemberRequestDto memberRequestDto)
    {
        return ResponseEntity.ok(memberService.update(id, memberRequestDto));
    }

    @GetMapping ("/{id}")
    public ResponseEntity<MemberResponseDto> getById (@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAll () {
        return ResponseEntity.ok(memberService.getAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<MemberResponseDto>> getAllActive() {
        return ResponseEntity.ok(memberService.getAllActive());
    }

    @GetMapping("/expired")
    public ResponseEntity<List<MemberResponseDto>> getAllExpiredPayments() {
        return ResponseEntity.ok(memberService.getAllExpiredPayments());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{memberId}/payments")
    public ResponseEntity<List<PaymentResponseDto>> getAllPaymentsByMemberId(
            @PathVariable Long memberId) {
        List<PaymentResponseDto> payments = memberService.getAllPaymentsByMemberId(memberId);
        return ResponseEntity.ok(payments);
    }
    @GetMapping ("/{dni}")
    public ResponseEntity<MemberResponseDto> getByDni(@PathVariable String dni) {
        return ResponseEntity.ok(memberService.getByDni(dni));
    }

}
