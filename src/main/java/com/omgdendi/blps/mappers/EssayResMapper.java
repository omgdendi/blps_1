package com.omgdendi.blps.mappers;

import com.omgdendi.blps.dto.res.EssayResDto;
import com.omgdendi.blps.entity.EssayEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EssayResMapper {
    EssayResMapper INSTANCE = Mappers.getMapper(EssayResMapper.class);

    @Mapping(source = "category.name", target = "category")
    EssayResDto toDTO(EssayEntity essayEntity);
}
