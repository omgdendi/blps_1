package com.omgdendi.blps.service;

import com.omgdendi.blps.dto.req.CategoryReqDto;
import com.omgdendi.blps.dto.res.CategoryResDto;
import com.omgdendi.blps.entity.CategoryEntity;
import com.omgdendi.blps.exception.CategoryAlreadyExistException;
import com.omgdendi.blps.exception.CategoryNotFoundException;
import com.omgdendi.blps.mappers.CategoryReqMapper;
import com.omgdendi.blps.mappers.CategoryResMapper;
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
