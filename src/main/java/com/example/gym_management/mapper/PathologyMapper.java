package com.example.gym_management.mapper;

import com.example.gym_management.dto.request.PathologyRequestDto;
import com.example.gym_management.dto.response.PathologyResponseDto;
import com.example.gym_management.model.Pathology;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PathologyMapper {
    Pathology toEntity (PathologyRequestDto pathologyRequestDto);
    PathologyResponseDto toDto (Pathology pathology);
}
