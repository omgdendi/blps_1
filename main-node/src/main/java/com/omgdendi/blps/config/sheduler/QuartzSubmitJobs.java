package com.omgdendi.blps.config.sheduler;

import com.omgdendi.blps.config.sheduler.jobs.NotificationStatsJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzSubmitJobs {
    private static final String CRON_EVERY_FIVE_MINUTES = "0 0/5 * ? * * *";

    @Bean(name = "notificationStats")
    public JobDetailFactoryBean jobMemberStats() {
        return QuartzConfig.createJobDetail(NotificationStatsJob.class, "Notification Stats Job Class");
    }

    @Bean(name = "notificationStatsTrigger")
    public SimpleTriggerFactoryBean triggerMemberStats(@Qualifier("notificationStats") JobDetail jobDetail) {
        return QuartzConfig.createTrigger(jobDetail, 12000, "Member Statistics Trigger");
    }
}