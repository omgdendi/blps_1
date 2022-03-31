package com.omgdendi.blps.service;

import com.omgdendi.blps.dto.req.EssayReqDto;
import com.omgdendi.blps.dto.res.EssayResDto;
import com.omgdendi.blps.entity.*;
import com.omgdendi.blps.entity.types.EssayStatus;
import com.omgdendi.blps.exception.CategoryNotFoundException;
import com.omgdendi.blps.exception.EssayNotFoundException;
import com.omgdendi.blps.mappers.EssayResMapper;
import com.omgdendi.blps.repository.EssayRepo;
import com.omgdendi.blps.repository.EssayStatusRepo;
import com.omgdendi.blps.repository.NotificationRepo;
import com.omgdendi.blps.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EssayService {

    private EssayRepo essayRepo;

    private UserRepo userRepo;

    private CategoryService categoryService;

    private EssayStatusRepo essayStatusRepo;

    private NotificationRepo notificationRepo;

    @Autowired
    public EssayService(EssayRepo essayRepo, UserRepo userRepo, CategoryService categoryService, EssayStatusRepo essayStatusRepo, NotificationRepo notificationRepo) {
        this.essayRepo = essayRepo;
        this.userRepo = userRepo;
        this.categoryService = categoryService;
        this.essayStatusRepo = essayStatusRepo;
        this.notificationRepo = notificationRepo;
    }


    @Transactional
    public EssayResDto createApprovedEssay(EssayReqDto essay, String username) throws CategoryNotFoundException {
        UserEntity user = userRepo.findByUsername(username).get();
        EssayStatusEntity approvedStatus = essayStatusRepo.findByName(EssayStatus.approved.toString());

        CategoryEntity category = categoryService.getCategoryByName(essay.getCategoryName());
        EssayEntity entity = this.essayConvertor(essay, user, category, approvedStatus);
        return EssayResMapper.INSTANCE.toDTO(essayRepo.save(entity));
    }


    @Transactional
    public void setPassStatusToEssay(Integer id) {
        EssayEntity essay = essayRepo.findById(id).get();
        EssayStatusEntity status = essayStatusRepo.findByName(EssayStatus.approved.toString());
        essay.setStatus(status);
        NotificationEntity notification = new NotificationEntity();
        notification.setUser(essay.getUser());
        notification.setDescription("Ваш реферат " + essay.getTitle() + " принят модератором и успешно добавлен");
        essayRepo.save(essay);
        notificationRepo.save(notification);
    }

    @Transactional
    public void setFailStatusToEssay(Integer id) {
        EssayEntity essay = essayRepo.findById(id).get();
        EssayStatusEntity status = essayStatusRepo.findByName(EssayStatus.failed.toString());
        essay.setStatus(status);
        NotificationEntity notification = new NotificationEntity();
        notification.setUser(essay.getUser());
        notification.setDescription("К сожалению ваш реферат " + essay.getTitle() + " отклонен модератором");
        essayRepo.save(essay);
        notificationRepo.save(notification);
    }

    public EssayResDto sendEssayToCheck(EssayReqDto essay, String username) throws CategoryNotFoundException {
        UserEntity user = userRepo.findByUsername(username).get();
        CategoryEntity category = categoryService.getCategoryByName(essay.getCategoryName());
        EssayStatusEntity status = essayStatusRepo.findByName(EssayStatus.not_approved.toString());
        EssayEntity entity = this.essayConvertor(essay, user, category);
        entity.setStatus(status);
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
        EssayStatusEntity notApprovedStatus = essayStatusRepo.findByName(EssayStatus.not_approved.toString());
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
