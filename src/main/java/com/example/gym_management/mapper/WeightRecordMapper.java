package com.example.gym_management.mapper;


import com.example.gym_management.dto.request.WeightRecordRequestDto;
import com.example.gym_management.dto.response.WeightRecordResponseDto;
import com.example.gym_management.model.WeightRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeightRecordMapper {

    @Mapping(target = "id",           ignore = true)
    @Mapping(target = "healthRecord", ignore = true)
    WeightRecord toEntity (WeightRecordRequestDto dto);
    WeightRecordResponseDto toDto (WeightRecord weightRecord);
}
