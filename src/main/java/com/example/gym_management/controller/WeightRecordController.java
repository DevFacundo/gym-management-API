package com.example.gym_management.controller;

import com.example.gym_management.config.ApiPaths;
import com.example.gym_management.dto.request.WeightRecordRequestDto;
import com.example.gym_management.dto.response.WeightRecordResponseDto;
import com.example.gym_management.service.interfaces.WeightRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.WEIGHT_RECORD_BASE)
@RequiredArgsConstructor
public class WeightRecordController {

    private final WeightRecordService weightRecordService;

    @PostMapping
    public ResponseEntity<WeightRecordResponseDto> create(
            @RequestBody @Valid WeightRecordRequestDto dto) {
        return ResponseEntity.ok(weightRecordService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeightRecordResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid WeightRecordRequestDto dto) {
        return ResponseEntity.ok(weightRecordService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        weightRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-health-record/{healthRecordId}")
    public ResponseEntity<List<WeightRecordResponseDto>> getByHealthRecord(
            @PathVariable Long healthRecordId) {
        return ResponseEntity.ok(weightRecordService.getByHealthRecordId(healthRecordId));
    }
}