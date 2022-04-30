package com.omgdendi.blps.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.omgdendi.data.dto.common.ReportDto;
import com.omgdendi.data.dto.req.EssayReqDto;
import com.omgdendi.data.dto.res.EssayResDto;
import com.omgdendi.blps.entity.*;
import com.omgdendi.blps.exception.CategoryNotFoundException;
import com.omgdendi.blps.exception.EssayNotFoundException;
import com.omgdendi.blps.mappers.EssayResMapper;
import com.omgdendi.blps.repository.EssayRepo;
import com.omgdendi.blps.repository.EssayStatusRepo;
import com.omgdendi.blps.repository.NotificationRepo;
import com.omgdendi.blps.repository.UserRepo;
import com.omgdendi.blps.types.EssayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EssayService {

    private ReportService reportService;

    private EssayRepo essayRepo;

    private UserRepo userRepo;

    private CategoryService categoryService;

    private EssayStatusRepo essayStatusRepo;

    private NotificationRepo notificationRepo;

    private KafkaService kafkaService;


    @Autowired
    public EssayService(ReportService reportService, EssayRepo essayRepo, UserRepo userRepo, CategoryService categoryService, EssayStatusRepo essayStatusRepo, NotificationRepo notificationRepo, KafkaService kafkaService) {
        this.reportService = reportService;
        this.essayRepo = essayRepo;
        this.userRepo = userRepo;
        this.categoryService = categoryService;
        this.essayStatusRepo = essayStatusRepo;
        this.notificationRepo = notificationRepo;
        this.kafkaService = kafkaService;
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
        EssayStatusEntity approvedStatus = essayStatusRepo.findByName(EssayStatus.APPROVED);

        CategoryEntity category = categoryService.getCategoryByName(essay.getCategoryName());
        EssayEntity entity = this.essayConvertor(essay, user, category, approvedStatus);
        return EssayResMapper.INSTANCE.toDTO(essayRepo.save(entity));
    }

    public EssayResDto sendEssayToCheck(EssayReqDto essay, String username) throws CategoryNotFoundException, JsonProcessingException {
        UserEntity user = userRepo.findByUsername(username).get();
        CategoryEntity category = categoryService.getCategoryByName(essay.getCategoryName());
        EssayStatusEntity status = essayStatusRepo.findByName(EssayStatus.NOT_APPROVED);
        EssayEntity entity = this.essayConvertor(essay, user, category);
        entity.setStatus(status);
        EssayEntity essayEntity =essayRepo.save(entity);
        ReportDto reportDto = new ReportDto();
        reportDto.setEssayId(essayEntity.getId());
        kafkaService.send(reportDto);
        return EssayResMapper.INSTANCE.toDTO(essayEntity);
    }

    private EssayEntity essayConvertor(EssayReqDto essay, UserEntity user, CategoryEntity category) {
        EssayEntity entity = new EssayEntity();
        entity.setTitle(essay.getTitle());
        entity.setAuthor(essay.getAuthor());
        entity.setContent(essay.getContent());
        entity.setCategory(category);
        entity.setUser(user);
        entity.setDateLoad(new Date());
        EssayStatusEntity notApprovedStatus = essayStatusRepo.findByName(EssayStatus.NOT_APPROVED);
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
