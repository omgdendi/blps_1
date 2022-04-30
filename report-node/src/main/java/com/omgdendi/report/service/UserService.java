package com.omgdendi.report.service;

import com.omgdendi.data.dto.res.NotificationResDto;
import com.omgdendi.report.entity.NotificationEntity;
import com.omgdendi.report.entity.UserEntity;
import com.omgdendi.report.mappers.NotificationResMapper;
import com.omgdendi.report.repository.NotificationRepo;
import com.omgdendi.report.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final NotificationRepo notificationRepo;

    @Autowired
    public UserService(UserRepo userRepo, NotificationRepo notificationRepo) {
        this.userRepo = userRepo;
        this.notificationRepo = notificationRepo;
    }

    public UserEntity findByUsername(String username) {
        UserEntity result = userRepo.findByUsername(username).orElse(null);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    public List<NotificationResDto> getNotification(String username) {
        UserEntity user = userRepo.findByUsername(username).get();
        List<NotificationEntity> notifications = notificationRepo.findByUser(user);
        return notifications.stream().map(notification -> NotificationResMapper.INSTANCE.toDTO(notification)).collect(Collectors.toList());
    }
}
