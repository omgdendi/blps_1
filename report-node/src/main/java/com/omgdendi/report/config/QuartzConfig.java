package com.omgdendi.report.config;

import com.omgdendi.blps.jobs.NotificationStatsJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
@Slf4j
public class QuartzConfig {

    @Value("${app.name}")
    private String appName;

    @Bean
    public JobDetail jobDetail() {
        if (appName.equals("blps")) {
            return newJob().ofType(NotificationStatsJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_Job_Detail"))
                    .withDescription("Invoke Advert Job service...").build();
        }
        return null;
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        if (appName.equals("blps")) {
            int frequencyInHours = 24;
            log.info("Configuring trigger to fire every {} hours", frequencyInHours);

            return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_Trigger")).withDescription("Advert trigger")
                    .withSchedule(simpleSchedule().withIntervalInHours(frequencyInHours).repeatForever()).build();
        }
        return null;
    }
}
