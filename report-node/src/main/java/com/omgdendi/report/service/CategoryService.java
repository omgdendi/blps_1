package com.omgdendi.report.service;


import com.omgdendi.data.dto.req.CategoryReqDto;
import com.omgdendi.data.dto.res.CategoryResDto;
import com.omgdendi.report.entity.CategoryEntity;
import com.omgdendi.report.exception.CategoryAlreadyExistException;
import com.omgdendi.report.exception.CategoryNotFoundException;
import com.omgdendi.report.mappers.CategoryReqMapper;
import com.omgdendi.report.mappers.CategoryResMapper;
import com.omgdendi.report.repository.CategoryRepo;
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


    public CategoryReqDto createCategory(CategoryReqDto category) throws CategoryAlreadyExistException {
        if (categoryRepo.findByName(category.getName()).isPresent()) {
            throw new CategoryAlreadyExistException();
        }
        return CategoryReqMapper.INSTANCE.toDTO(categoryRepo.save(CategoryReqMapper.INSTANCE.toEntity(category)));
    }

    public List<CategoryResDto> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryRepo.findAll();
        return categoryEntities.stream().map(category -> this.convertTo(category)).collect(Collectors.toList());
    }

    public CategoryEntity getCategoryByName(String name) throws CategoryNotFoundException {
        CategoryEntity category = categoryRepo.findByName(name).orElseThrow(() -> new CategoryNotFoundException());
        return category;
    }

    private CategoryResDto convertTo(CategoryEntity categoryEntity) {
        CategoryResDto category = CategoryResMapper.INSTANCE.toDTO(categoryEntity);
        category.setCount(categoryEntity.getEssay().size());
        return category;
    }
}
