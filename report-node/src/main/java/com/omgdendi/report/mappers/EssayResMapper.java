package com.omgdendi.report.mappers;


import com.omgdendi.data.dto.res.EssayResDto;
import com.omgdendi.report.entity.EssayEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EssayResMapper {
    EssayResMapper INSTANCE = Mappers.getMapper(EssayResMapper.class);

    @Mapping(source = "category.name", target = "category")
    EssayResDto toDTO(EssayEntity essayEntity);
}
