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

    @GetMapping
    public ResponseEntity<List<PathologyResponseDto>> getAll() {
        return ResponseEntity.ok(pathologyService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PathologyResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pathologyService.getById(id));
    }

    @GetMapping("/by-name")
    public ResponseEntity<PathologyResponseDto> getByName(@RequestParam String name) {
        return ResponseEntity.ok(pathologyService.getByName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pathologyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
