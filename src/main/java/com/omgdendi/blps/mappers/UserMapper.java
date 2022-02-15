package com.omgdendi.blps.mappers;

import com.omgdendi.blps.dto.CategoryDTO;
import com.omgdendi.blps.dto.UserDTO;
import com.omgdendi.blps.entity.CategoryEntity;
import com.omgdendi.blps.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(UserEntity userEntity);
    UserEntity toEntity(UserDTO userDTO);
}
