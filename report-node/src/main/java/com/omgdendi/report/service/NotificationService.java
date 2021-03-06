package com.omgdendi.report.service;


import com.omgdendi.report.entity.NotificationEntity;
import com.omgdendi.report.entity.UserEntity;
import com.omgdendi.report.repository.NotificationRepo;
import com.omgdendi.report.repository.RoleRepo;
import com.omgdendi.report.repository.UserRepo;
import com.omgdendi.report.types.EssayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepo notificationRepo;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Autowired
    public NotificationService(NotificationRepo notificationRepo, UserRepo userRepo, RoleRepo roleRepo) {
        this.notificationRepo = notificationRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public void sendDailyStatNotification() {
        List<UserEntity> users = userRepo.findAll();
        users.stream().forEach(user -> {
            NotificationEntity notification = new NotificationEntity();
            notification.setUser(user);
            long essayCount = user.getEssays().stream().count();
            long essayApprovedCount = user.getEssays().stream().filter(essay -> essay.getStatus().toString() == EssayStatus.APPROVED).count();
            long essayNotApprovedCount = essayCount - essayApprovedCount;
            notification.setDescription("Hi! It's the daily stats for you. Now you have "
                    + essayApprovedCount + " essays in review and " + essayNotApprovedCount
                    + " essays posted");
            notificationRepo.save(notification);
        });
    }
}
