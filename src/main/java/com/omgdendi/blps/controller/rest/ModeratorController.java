package com.omgdendi.blps.controller.rest;

import com.omgdendi.blps.dto.req.CategoryReqDto;
import com.omgdendi.blps.dto.req.EssayReqDto;
import com.omgdendi.blps.dto.res.EssayResDto;
import com.omgdendi.blps.service.CategoryService;
import com.omgdendi.blps.service.EssayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/moderator")
public class ModeratorController {

    private final EssayService essayService;
    private final CategoryService categoryService;

    @Autowired
    public ModeratorController(EssayService essayService, CategoryService categoryService) {
        this.essayService = essayService;
        this.categoryService = categoryService;
    }


    @Operation(summary = "Создать письменный материал")
    @PostMapping("/essay/create")
    public ResponseEntity<EssayResDto> createEssay(@RequestBody EssayReqDto essay) {
        return ResponseEntity.ok(essayService.createEssay(essay));
    }

    @Operation(summary = "Создать категорию")
    @PostMapping("category/create")
    public ResponseEntity<?> createCategory(@RequestBody CategoryReqDto category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok("Категория была успешно создана");
    }

    @Operation(summary = "Установить статус 'Принят' письменному материалу")
    @PutMapping("/essay/status/pass/{id}")
    public ResponseEntity<?> setPassStatusToEssay(@Parameter(description = "id письменного материла")
                                                  @PathVariable int id) {
        essayService.setPassStatusToEssay(id);
        return ResponseEntity.ok("Статус успешно был обновлен на 'Принят'");
    }

    @Operation(summary = "Установить статус 'Не принят' письменному материалу")
    @PutMapping("/essay/status/fail/{id}")
    public ResponseEntity<?> setFailStatusToEssay(@Parameter(description = "id письменного материла")
                                                  @PathVariable int id) {
        essayService.setFailStatusToEssay(id);
        return ResponseEntity.ok("Статус успешно был обновлен на 'Не принят'");
    }

}
