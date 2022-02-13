package com.omgdendi.blps.service;

import com.omgdendi.blps.dto.EssayToGetDto;
import com.omgdendi.blps.entity.CategoryEntity;
import com.omgdendi.blps.entity.EssayEntity;
import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.dto.EssayDto;
import com.omgdendi.blps.exception.CategoryNotFoundException;
import com.omgdendi.blps.repository.EssayRepo;
import com.omgdendi.blps.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EssayService {

    @Autowired
    private EssayRepo essayRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryService categoryService;


    public EssayDto createEssay(EssayDto essay) throws CategoryNotFoundException {
        UserEntity user = userRepo.findById(essay.getUserId()).get();
        CategoryEntity category = categoryService.getCategoryByName(essay.getCategoryName());
        EssayEntity entity = new EssayEntity();
        entity.setTitle(essay.getTitle());
        entity.setAuthor(essay.getAuthor());
        entity.setContent(essay.getContent());
        entity.setCategory(category);
        entity.setUser(user);
        entity.setDate_load(new Date());
        entity.setDate_create(new Date());
        return this.convertTo(essayRepo.save(entity));
    }

    public List<EssayToGetDto> getEssaysByTitle(String title) {
        List<EssayEntity> essays = essayRepo.findAllByTitle(title);
        return essays.stream().map(essay -> this.convertToGet(essay)).collect(Collectors.toList());
    }

    private EssayDto convertTo(EssayEntity entity) {
        EssayDto essay = new EssayDto();
        essay.setId(entity.getId());
        essay.setTitle(entity.getTitle());
        essay.setAuthor(entity.getAuthor());
        essay.setCategoryName(entity.getCategory().getName());
        essay.setContent(entity.getContent());
        essay.setUserId(entity.getUser().getId());
        essay.setDateCreate(entity.getDate_create());
        return essay;
    }

    private EssayToGetDto convertToGet(EssayEntity entity) {
        EssayToGetDto essay = new EssayToGetDto();
        essay.setId(entity.getId());
        essay.setTitle(entity.getTitle());
        essay.setAuthor(entity.getAuthor());
        essay.setContent(entity.getContent());
        essay.setDateCreate(entity.getDate_create());
        return essay;
    }
}
