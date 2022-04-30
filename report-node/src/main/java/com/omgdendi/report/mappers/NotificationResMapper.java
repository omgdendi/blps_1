package com.omgdendi.report.mappers;

import com.omgdendi.data.dto.res.NotificationResDto;
import com.omgdendi.report.entity.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationResMapper {
    NotificationResMapper INSTANCE = Mappers.getMapper(NotificationResMapper.class);

    NotificationResDto toDTO(NotificationEntity notificationEntity);
}