package com.omgdendi.blps.service;

import com.omgdendi.blps.entity.CategoryEntity;
import com.omgdendi.blps.exception.CategoryAlreadyExistException;
import com.omgdendi.blps.exception.CategoryNotFoundException;
import com.omgdendi.blps.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    public CategoryEntity createCategory(CategoryEntity category) throws CategoryAlreadyExistException {
        if (categoryRepo.findByName(category.getName()) != null) {
            throw new CategoryAlreadyExistException("Категория уже существует");
        }
        return categoryRepo.save(category);
    }

    public List<CategoryEntity> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryRepo.findAll();
        return categoryEntities;
    }

    public CategoryEntity getCategoryByName(String name) throws CategoryNotFoundException {
        CategoryEntity category = categoryRepo.findByName(name);
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        return category;
    }

    public CategoryEntity getCategoryById(Long id) throws CategoryNotFoundException {
        CategoryEntity category = categoryRepo.findById(id).orElseThrow(CategoryNotFoundException::new);
        return category;
    }

}
