package com.omgdendi.blps.mappers;

import com.omgdendi.blps.dto.CategoryDTO;
import com.omgdendi.blps.dto.CategoryToGetDTO;
import com.omgdendi.blps.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryToGetMapper {
    CategoryToGetMapper INSTANCE = Mappers.getMapper(CategoryToGetMapper.class);

    CategoryToGetDTO toDTO(CategoryEntity categoryEntity);
}