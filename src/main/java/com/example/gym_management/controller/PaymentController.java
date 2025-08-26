package com.example.gym_management.controller;

import com.example.gym_management.config.ApiPaths;
import com.example.gym_management.dto.request.PaymentRequestDto;
import com.example.gym_management.dto.response.PaymentResponseDto;
import com.example.gym_management.service.interfaces.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.PAYMENT_BASE)
@RequiredArgsConstructor
public class PaymentController {


    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> create(@RequestBody @Valid PaymentRequestDto dto) {
        return ResponseEntity.ok(paymentService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> update(@PathVariable Long id,
                                                     @RequestBody @Valid PaymentRequestDto dto) {
        return ResponseEntity.ok(paymentService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<PaymentResponseDto>> getAllByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(paymentService.getAllByMemberId(memberId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PaymentResponseDto>> getByPaymentDateBetween(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(paymentService.getByPaymentDateBetween(startDate, endDate));
    }

//http://localhost:8080/api/payments/expired?before=2025-07-31
    @GetMapping("/expired")
    public ResponseEntity<List<PaymentResponseDto>> getByExpirationDateBefore(
            @RequestParam(required = false) LocalDate before) {
        LocalDate cutoff = before != null ? before : LocalDate.now();
        return ResponseEntity.ok(paymentService.getByExpirationDateBefore(cutoff));
    }

//http://localhost:8080/api/payments/earnings?year=2025&month=8
    @GetMapping("/earnings")
    public ResponseEntity<BigDecimal> calculateEarningsForMonth(@RequestParam Integer year,
                                                                @RequestParam Integer month) {
        return ResponseEntity.ok(paymentService.calculateEarningsForMonth(year, month));
    }
}
