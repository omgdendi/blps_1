package com.omgdendi.blps.mappers;

import com.omgdendi.blps.dto.req.CategoryReqDto;
import com.omgdendi.blps.entity.CategoryEntity;
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
