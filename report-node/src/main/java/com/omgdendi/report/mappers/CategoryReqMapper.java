package com.omgdendi.report.mappers;

import com.omgdendi.data.dto.req.CategoryReqDto;
import com.omgdendi.report.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CategoryReqMapper {
    CategoryReqMapper INSTANCE = Mappers.getMapper(CategoryReqMapper.class);

    CategoryReqDto toDTO(CategoryEntity categoryEntity);

    CategoryEntity toEntity(CategoryReqDto categoryReqDto);
}
