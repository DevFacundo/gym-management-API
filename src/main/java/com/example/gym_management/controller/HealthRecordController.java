package com.example.gym_management.controller;

import com.example.gym_management.config.ApiPaths;
import com.example.gym_management.dto.request.HealthRecordRequestDto;
import com.example.gym_management.dto.response.HealthRecordResponseDto;
import com.example.gym_management.service.interfaces.HealthRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.HEALTH_RECORD_BASE)
@RequiredArgsConstructor
public class HealthRecordController {

    private final HealthRecordService healthRecordService;

    @PostMapping
    public ResponseEntity<HealthRecordResponseDto> create(@RequestBody @Valid HealthRecordRequestDto dto) {
        return ResponseEntity.ok(healthRecordService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HealthRecordResponseDto> update(@PathVariable Long id,
                                                          @RequestBody @Valid HealthRecordRequestDto dto) {
        return ResponseEntity.ok(healthRecordService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthRecordResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(healthRecordService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<HealthRecordResponseDto>> getAll() {
        return ResponseEntity.ok(healthRecordService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        healthRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
