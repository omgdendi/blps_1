package com.omgdendi.report.service;

import com.omgdendi.report.entity.EssayEntity;
import com.omgdendi.report.entity.EssayStatusEntity;
import com.omgdendi.report.entity.NotificationEntity;
import com.omgdendi.report.entity.ReportEntity;
import com.omgdendi.report.repository.EssayRepo;
import com.omgdendi.report.repository.EssayStatusRepo;
import com.omgdendi.report.repository.NotificationRepo;
import com.omgdendi.report.types.EssayStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EssayService {

    private final EssayRepo essayRepo;
    private final EssayStatusRepo essayStatusRepo;
    private final NotificationRepo notificationRepo;
    private final ReportService reportService;

    public EssayService(EssayRepo essayRepo, EssayStatusRepo essayStatusRepo, NotificationRepo notificationRepo, ReportService reportService) {
        this.essayRepo = essayRepo;
        this.essayStatusRepo = essayStatusRepo;
        this.notificationRepo = notificationRepo;
        this.reportService = reportService;
    }


    @Transactional
    public void setPassStatusToEssay(Integer id) {
        ReportEntity report = reportService.findById(id);
        EssayEntity essay = report.getEssay();
        EssayStatusEntity status = essayStatusRepo.findByName(EssayStatus.APPROVED);
        essay.setStatus(status);
        NotificationEntity notification = new NotificationEntity();
        notification.setUser(essay.getUser());
        notification.setDescription("Ваш реферат " + essay.getTitle() + " принят модератором и успешно добавлен");
        essayRepo.save(essay);
        notificationRepo.save(notification);
        reportService.setNotActiveStatusToReport(report);
    }

    @Transactional
    public void setFailStatusToEssay(Integer id) {
        ReportEntity report = reportService.findById(id);
        EssayEntity essay = report.getEssay();
        EssayStatusEntity status = essayStatusRepo.findByName(EssayStatus.FAILED);
        essay.setStatus(status);
        NotificationEntity notification = new NotificationEntity();
        notification.setUser(essay.getUser());
        notification.setDescription("К сожалению ваш реферат " + essay.getTitle() + " отклонен модератором");
        essayRepo.save(essay);
        notificationRepo.save(notification);
        reportService.setNotActiveStatusToReport(report);
    }
}
