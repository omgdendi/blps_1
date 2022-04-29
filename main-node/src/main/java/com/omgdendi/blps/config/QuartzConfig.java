package com.omgdendi.blps.config;

import com.omgdendi.blps.jobs.NotificationStatsJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.Trigger;

@Configuration
@Slf4j
public class QuartzConfig {
    @Bean
    public JobDetail jobDetail() {
        return newJob().ofType(NotificationStatsJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_Job_Detail"))
                .withDescription("Invoke Advert Job service...").build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {

        int frequencyInHours = 24;
        log.info("Configuring trigger to fire every {} hours", frequencyInHours);

        return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_Trigger")).withDescription("Advert trigger")
                .withSchedule(simpleSchedule().withIntervalInHours(frequencyInHours).repeatForever()).build();
    }
}
