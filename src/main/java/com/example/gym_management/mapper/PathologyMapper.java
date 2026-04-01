package com.example.gym_management.mapper;

import com.example.gym_management.dto.request.PathologyRequestDto;
import com.example.gym_management.dto.response.PathologyResponseDto;
import com.example.gym_management.model.Pathology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PathologyMapper {

    @Mapping(target = "id",           ignore = true)
    @Mapping(target = "healthRecord", ignore = true)
    @Mapping(target = "active",       ignore = true)
    Pathology toEntity (PathologyRequestDto pathologyRequestDto);
    PathologyResponseDto toDto (Pathology pathology);
}
