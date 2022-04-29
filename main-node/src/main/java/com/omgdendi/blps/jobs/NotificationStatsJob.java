package com.omgdendi.blps.jobs;

import com.omgdendi.blps.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@DisallowConcurrentExecution
public class NotificationStatsJob implements Job {
    private NotificationService notificationService;

    @Autowired
    public NotificationStatsJob(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Job starts");
        notificationService.sendDailyStatNotification();
        log.info("Job completed");
    }
}
