package com.omgdendi.blps.mappers;

import com.omgdendi.blps.dto.res.CategoryResDto;
import com.omgdendi.blps.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryResMapper {
    CategoryResMapper INSTANCE = Mappers.getMapper(CategoryResMapper.class);

    @Mapping(source = "id", target = "id")
    CategoryResDto toDTO(CategoryEntity categoryEntity);
}