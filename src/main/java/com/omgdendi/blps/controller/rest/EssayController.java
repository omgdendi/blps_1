package com.omgdendi.blps.controller.rest;

import com.omgdendi.blps.dto.EssayDTO;
import com.omgdendi.blps.dto.EssayToGetDTO;
import com.omgdendi.blps.service.EssayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/essay")
public class EssayController {

    private final EssayService essayService;

    @Autowired
    public EssayController(EssayService essayService) {
        this.essayService = essayService;
    }

    @Operation(summary = "Создать письменный материал")
    @PostMapping
    public ResponseEntity<EssayDTO> createEssay(@RequestBody EssayDTO essay) {
        return ResponseEntity.ok(essayService.createEssay(essay));
    }

    @Operation(summary = "Получить письменный материал по указанному id")
    @GetMapping("/{id}")
    public ResponseEntity<EssayToGetDTO> getEssay(@Parameter(description = "id письменного материла")
                                                      @PathVariable int id) {
        return ResponseEntity.ok(essayService.getEssay(id));
    }

    @Operation(summary = "Получить все письменные материала по совпадению с указанным заголовком")
    @GetMapping("/title")
    public ResponseEntity<List<EssayToGetDTO>> getEssaysByTitle(
            @Parameter(description = "заголовок письменного материла")
            @RequestParam String title) {
        return ResponseEntity.ok(essayService.getEssaysByTitle(title));
    }

    @Operation(summary = "Получить все письменные материалы по указанной категории")
    @GetMapping("/category/{id}")
    public ResponseEntity<List<EssayToGetDTO>> getEssaysByCategory(
            @Parameter(description = "id категории письменного материла")
            @PathVariable int id) {
        return ResponseEntity.ok(essayService.getEssaysByCategory(id));
    }

    @Operation(summary = "Получить определенное количество письменныех материалов, " +
            "отсортированных по дате загрузки (от более ранних до более поздних)")
    @GetMapping("/recent/{amount}")
    public ResponseEntity<List<EssayToGetDTO>> getRecentEssays(  @Parameter(description = "количество письменного материла")
                                                                             int amount) {
        return ResponseEntity.ok(essayService.getRecentEssays(amount));
    }
}
