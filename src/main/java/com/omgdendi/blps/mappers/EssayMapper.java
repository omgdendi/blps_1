package com.omgdendi.blps.mappers;

import com.omgdendi.blps.dto.EssayDTO;
import com.omgdendi.blps.entity.EssayEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EssayMapper {
    EssayMapper INSTANCE = Mappers.getMapper(EssayMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "category.name", target = "categoryName")
    EssayDTO toDTO(EssayEntity essayEntity);
}
