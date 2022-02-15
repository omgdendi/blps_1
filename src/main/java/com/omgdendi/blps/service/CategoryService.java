package com.omgdendi.blps.service;

import com.omgdendi.blps.dto.CategoryDTO;
import com.omgdendi.blps.dto.CategoryToGetDTO;
import com.omgdendi.blps.entity.CategoryEntity;
import com.omgdendi.blps.exception.CategoryAlreadyExistException;
import com.omgdendi.blps.exception.CategoryNotFoundException;
import com.omgdendi.blps.mappers.CategoryMapper;
import com.omgdendi.blps.mappers.CategoryToGetMapper;
import com.omgdendi.blps.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    public CategoryDTO createCategory(CategoryDTO category) throws CategoryAlreadyExistException {
        if (categoryRepo.findByName(category.getName()).isPresent()) {
            throw new CategoryAlreadyExistException();
        }
        return CategoryMapper.INSTANCE.toDTO(categoryRepo.save(CategoryMapper.INSTANCE.toEntity(category)));
    }

    public List<CategoryToGetDTO> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryRepo.findAll();
        return categoryEntities.stream().map(category -> this.convertTo(category)).collect(Collectors.toList());
    }

    public CategoryEntity getCategoryByName(String name) throws CategoryNotFoundException {
        CategoryEntity category = categoryRepo.findByName(name).orElseThrow(() -> new CategoryNotFoundException());
        return category;
    }

    private CategoryToGetDTO convertTo(CategoryEntity categoryEntity) {
        CategoryToGetDTO category = CategoryToGetMapper.INSTANCE.toDTO(categoryEntity);
        category.setCount(categoryEntity.getEssay().size());
        return category;
    }
}
