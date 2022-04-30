package com.omgdendi.report.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.omgdendi.data.dto.common.ReportDto;
import com.omgdendi.data.dto.req.EssayReqDto;
import com.omgdendi.data.dto.res.EssayResDto;
import com.omgdendi.report.entity.*;
import com.omgdendi.report.exception.CategoryNotFoundException;
import com.omgdendi.report.exception.EssayNotFoundException;
import com.omgdendi.report.mappers.EssayResMapper;
import com.omgdendi.report.repository.EssayRepo;
import com.omgdendi.report.repository.EssayStatusRepo;
import com.omgdendi.report.repository.NotificationRepo;
import com.omgdendi.report.repository.UserRepo;
import com.omgdendi.report.types.EssayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EssayService {

    private final EssayRepo essayRepo;
    private final EssayStatusRepo essayStatusRepo;
    private final NotificationRepo notificationRepo;
    private final ReportService reportService;
    private final UserRepo userRepo;
    private final CategoryService categoryService;

    public EssayService(EssayRepo essayRepo, EssayStatusRepo essayStatusRepo, NotificationRepo notificationRepo, ReportService reportService, UserRepo userRepo, CategoryService categoryService) {
        this.essayRepo = essayRepo;
        this.essayStatusRepo = essayStatusRepo;
        this.notificationRepo = notificationRepo;
        this.reportService = reportService;
        this.userRepo = userRepo;
        this.categoryService = categoryService;
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


    public EssayResDto createApprovedEssay(EssayReqDto essay, String username) throws CategoryNotFoundException {
        UserEntity user = userRepo.findByUsername(username).get();
        EssayStatusEntity approvedStatus = essayStatusRepo.findByName(com.omgdendi.blps.types.EssayStatus.APPROVED);

        CategoryEntity category = categoryService.getCategoryByName(essay.getCategoryName());
        EssayEntity entity = this.essayConvertor(essay, user, category, approvedStatus);
        return EssayResMapper.INSTANCE.toDTO(essayRepo.save(entity));
    }


    private EssayEntity essayConvertor(EssayReqDto essay, UserEntity user, CategoryEntity category) {
        EssayEntity entity = new EssayEntity();
        entity.setTitle(essay.getTitle());
        entity.setAuthor(essay.getAuthor());
        entity.setContent(essay.getContent());
        entity.setCategory(category);
        entity.setUser(user);
        entity.setDateLoad(new Date());
        EssayStatusEntity notApprovedStatus = essayStatusRepo.findByName(com.omgdendi.blps.types.EssayStatus.NOT_APPROVED);
        if (notApprovedStatus != null) entity.setStatus(notApprovedStatus);
        return entity;
    }

    private EssayEntity essayConvertor(EssayReqDto essay, UserEntity user, CategoryEntity category, EssayStatusEntity status) {
        EssayEntity entity = new EssayEntity();
        entity.setTitle(essay.getTitle());
        entity.setAuthor(essay.getAuthor());
        entity.setContent(essay.getContent());
        entity.setCategory(category);
        entity.setUser(user);
        entity.setDateLoad(new Date());
        entity.setStatus(status);
        return entity;
    }

    public EssayResDto getEssay(Integer id) throws EssayNotFoundException {
        EssayEntity essay = essayRepo.findById(id).get();

        if (essay == null) {
            throw new EssayNotFoundException();
        }
        return EssayResMapper.INSTANCE.toDTO(essay);
    }

    public List<EssayResDto> getEssaysByTitle(String title) {
        List<EssayEntity> essays = essayRepo.findAllByTitle(title);
        return essays.stream().map(essay -> EssayResMapper.INSTANCE.toDTO(essay)).collect(Collectors.toList());
    }

    public List<EssayResDto> getEssaysByCategory(Integer id) {
        List<EssayEntity> essays = essayRepo.findAllByCategory(id);
        return essays.stream().map(essay -> EssayResMapper.INSTANCE.toDTO(essay)).collect(Collectors.toList());
    }

    public List<EssayResDto> getRecentEssays(int count) {
        List<EssayEntity> essays = essayRepo.findAllRecentEssays(count);
        return essays.stream().map(essay -> EssayResMapper.INSTANCE.toDTO(essay)).collect(Collectors.toList());
    }

}
