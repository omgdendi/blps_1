package com.omgdendi.blps.service;

import com.omgdendi.blps.dto.EssayToGetDTO;
import com.omgdendi.blps.entity.CategoryEntity;
import com.omgdendi.blps.entity.EssayEntity;
import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.dto.EssayDTO;
import com.omgdendi.blps.exception.CategoryNotFoundException;
import com.omgdendi.blps.exception.EssayNotFoundException;
import com.omgdendi.blps.mappers.EssayMapper;
import com.omgdendi.blps.mappers.EssayToGetMapper;
import com.omgdendi.blps.repository.EssayRepo;
import com.omgdendi.blps.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EssayService {

    private EssayRepo essayRepo;

    private UserRepo userRepo;

    private CategoryService categoryService;

    @Autowired
    public EssayService(EssayRepo essayRepo, UserRepo userRepo, CategoryService categoryService) {
        this.essayRepo = essayRepo;
        this.userRepo = userRepo;
        this.categoryService = categoryService;
    }

    public EssayDTO createEssay(EssayDTO essay) throws CategoryNotFoundException {
        UserEntity user = userRepo.findById(essay.getUserId()).get();
        CategoryEntity category = categoryService.getCategoryByName(essay.getCategoryName());
        EssayEntity entity = new EssayEntity();
        entity.setTitle(essay.getTitle());
        entity.setAuthor(essay.getAuthor());
        entity.setContent(essay.getContent());
        entity.setCategory(category);
        entity.setUser(user);
        entity.setDateLoad(new Date());
        return EssayMapper.INSTANCE.toDTO(essayRepo.save(entity));
    }

    public EssayToGetDTO getEssay(Long id) throws EssayNotFoundException {
        EssayEntity essay = essayRepo.findById(id).get();
        if (essay == null) {
            throw new EssayNotFoundException();
        }
        return EssayToGetMapper.INSTANCE.toDTO(essay);
    }

    public List<EssayToGetDTO> getEssaysByTitle(String title) {
        List<EssayEntity> essays = essayRepo.findAllByTitle(title);
        return essays.stream().map(essay -> EssayToGetMapper.INSTANCE.toDTO(essay)).collect(Collectors.toList());
    }

    public List<EssayToGetDTO> getEssaysByCategory(Long categoryId) {
        List<EssayEntity> essays = essayRepo.findAllByCategory(categoryId);
        return essays.stream().map(essay -> EssayToGetMapper.INSTANCE.toDTO(essay)).collect(Collectors.toList());
    }

}
