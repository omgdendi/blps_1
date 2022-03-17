package com.omgdendi.blps.service;

import com.omgdendi.blps.dto.req.EssayReqDto;
import com.omgdendi.blps.dto.res.EssayResDto;
import com.omgdendi.blps.entity.CategoryEntity;
import com.omgdendi.blps.entity.EssayEntity;
import com.omgdendi.blps.entity.EssayStatusEntity;
import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.exception.CategoryNotFoundException;
import com.omgdendi.blps.exception.EssayNotFoundException;
import com.omgdendi.blps.mappers.EssayReqMapper;
import com.omgdendi.blps.mappers.EssayResMapper;
import com.omgdendi.blps.repository.EssayRepo;
import com.omgdendi.blps.repository.EssayStatusRepo;
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

    @Autowired
    public EssayService(EssayRepo essayRepo, UserRepo userRepo, CategoryService categoryService, EssayStatusRepo essayStatusRepo) {
        this.essayRepo = essayRepo;
        this.userRepo = userRepo;
        this.categoryService = categoryService;
        this.essayStatusRepo = essayStatusRepo;
    }

    public EssayResDto createEssay(EssayReqDto essay) throws CategoryNotFoundException {
        UserEntity user = userRepo.findById(essay.getUserId()).get();
        CategoryEntity category = categoryService.getCategoryByName(essay.getCategoryName());
        EssayEntity entity = this.essayConvertor(essay, user, category);
        return EssayResMapper.INSTANCE.toDTO(essayRepo.save(entity));
    }

    @Transactional
    public void setPassStatusToEssay(Integer id) {
        EssayEntity essay = essayRepo.findById(id).get();
        EssayStatusEntity status = essayStatusRepo.findByName("PASS");
        essay.setStatus(status);
        essayRepo.save(essay);
    }

    @Transactional
    public void setFailStatusToEssay(Integer id) {
        EssayEntity essay = essayRepo.findById(id).get();
        EssayStatusEntity status = essayStatusRepo.findByName("FAIL");
        essay.setStatus(status);
        essayRepo.save(essay);
    }


    public EssayResDto sendEssayToCheck(EssayReqDto essay) throws CategoryNotFoundException {
        UserEntity user = userRepo.findById(essay.getUserId()).get();
        CategoryEntity category = categoryService.getCategoryByName(essay.getCategoryName());
        EssayStatusEntity status = essayStatusRepo.findByName("CHECK");
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
