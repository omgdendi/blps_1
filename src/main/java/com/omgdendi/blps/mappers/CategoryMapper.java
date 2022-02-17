package com.omgdendi.blps.mappers;

import com.omgdendi.blps.dto.CategoryDTO;
import com.omgdendi.blps.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toDTO(CategoryEntity categoryEntity);

    CategoryEntity toEntity(CategoryDTO categoryDTO);
}