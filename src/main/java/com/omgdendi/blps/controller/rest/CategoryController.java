package com.omgdendi.blps.controller.rest;

import com.omgdendi.blps.dto.res.CategoryResDto;
import com.omgdendi.blps.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Operation(summary = "Получить все категории")
    @GetMapping
    public ResponseEntity<List<CategoryResDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
