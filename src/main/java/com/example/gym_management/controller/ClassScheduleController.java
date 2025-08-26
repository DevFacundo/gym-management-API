package com.example.gym_management.controller;

import com.example.gym_management.config.ApiPaths;
import com.example.gym_management.dto.request.ClassScheduleRequestDto;
import com.example.gym_management.dto.response.ClassScheduleResponseDto;
import com.example.gym_management.dto.response.MemberResponseDto;
import com.example.gym_management.service.interfaces.ClassScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.CLASS_SCHEDULE_BASE)
@RequiredArgsConstructor
public class ClassScheduleController {

    private final ClassScheduleService classScheduleService;

    @PostMapping
    public ResponseEntity<ClassScheduleResponseDto> create(@RequestBody @Valid ClassScheduleRequestDto dto) {
        return ResponseEntity.ok(classScheduleService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassScheduleResponseDto> update(@PathVariable Long id,
                                                           @RequestBody @Valid ClassScheduleRequestDto dto) {
        return ResponseEntity.ok(classScheduleService.update(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<ClassScheduleResponseDto>> getAll() {
        return ResponseEntity.ok(classScheduleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassScheduleResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(classScheduleService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classScheduleService.delete(id);
        return ResponseEntity.noContent().build(); // 204 si elimina; tu handler devolverá 404 si no existe
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<MemberResponseDto>> getMembers(@PathVariable Long id) {
        return ResponseEntity.ok(classScheduleService.getAllMembersByClassScheduleId(id));
    }








}
