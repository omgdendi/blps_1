package com.omgdendi.blps.controller;

import com.omgdendi.blps.entity.CategoryEntity;
import com.omgdendi.blps.exception.CategoryAlreadyExistException;
import com.omgdendi.blps.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity createCategory(@RequestBody CategoryEntity category) {
        try {
            categoryService.createCategory(category);
            return ResponseEntity.ok("Категория была успешно создана");
        } catch (CategoryAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {
            return ResponseEntity.ok(categoryService.getAllCategories());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
