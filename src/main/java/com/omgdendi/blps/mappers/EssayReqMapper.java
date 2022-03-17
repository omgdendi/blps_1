package com.omgdendi.blps.mappers;

import com.omgdendi.blps.dto.req.EssayReqDto;
import com.omgdendi.blps.entity.EssayEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EssayReqMapper {
    EssayReqMapper INSTANCE = Mappers.getMapper(EssayReqMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "category.name", target = "categoryName")
    EssayReqDto toDTO(EssayEntity essayEntity);
}
