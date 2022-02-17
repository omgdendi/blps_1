package com.omgdendi.blps.controller.rest;

import com.omgdendi.blps.dto.CategoryDTO;
import com.omgdendi.blps.dto.CategoryToGetDTO;
import com.omgdendi.blps.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok("Категория была успешно создана");
    }

    @GetMapping
    public ResponseEntity<List<CategoryToGetDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
