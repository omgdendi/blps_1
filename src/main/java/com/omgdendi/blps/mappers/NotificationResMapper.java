package com.omgdendi.blps.mappers;

import com.omgdendi.blps.dto.res.NotificationResDto;
import com.omgdendi.blps.entity.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationResMapper {
    NotificationResMapper INSTANCE = Mappers.getMapper(NotificationResMapper.class);

    NotificationResDto toDTO(NotificationEntity notificationEntity);
}
