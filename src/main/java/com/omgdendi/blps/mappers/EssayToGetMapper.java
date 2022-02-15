package com.omgdendi.blps.mappers;

import com.omgdendi.blps.dto.EssayToGetDTO;
import com.omgdendi.blps.entity.EssayEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EssayToGetMapper {
    EssayToGetMapper INSTANCE = Mappers.getMapper(EssayToGetMapper.class);

    @Mapping(source = "category.name", target = "category")
    EssayToGetDTO toDTO(EssayEntity essayEntity);
}
