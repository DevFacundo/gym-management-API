package com.example.gym_management.controller;

import com.example.gym_management.config.ApiPaths;
import com.example.gym_management.dto.request.PathologyRequestDto;
import com.example.gym_management.dto.response.PathologyResponseDto;
import com.example.gym_management.service.interfaces.PathologyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.PATHOLOGY_BASE)
@RequiredArgsConstructor
public class PathologyController {

    private final PathologyService pathologyService;

    @PostMapping
    public ResponseEntity<PathologyResponseDto> create(@RequestBody @Valid PathologyRequestDto dto) {
        return ResponseEntity.ok(pathologyService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PathologyResponseDto> update(@PathVariable Long id,
                                                       @RequestBody @Valid PathologyRequestDto dto) {
        return ResponseEntity.ok(pathologyService.update(id, dto));
    }

    // PATCH para activar/desactivar sin mandar el body completo
    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<PathologyResponseDto> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(pathologyService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pathologyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Útil para cargar patologías de un alumno específico sin ir por Member
    @GetMapping("/by-health-record/{healthRecordId}")
    public ResponseEntity<List<PathologyResponseDto>> getByHealthRecord(
            @PathVariable Long healthRecordId) {
        return ResponseEntity.ok(pathologyService.getByHealthRecordId(healthRecordId));
    }
}
