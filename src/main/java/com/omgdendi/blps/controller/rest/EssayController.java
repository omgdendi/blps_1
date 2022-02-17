package com.omgdendi.blps.controller.rest;

import com.omgdendi.blps.dto.EssayDTO;
import com.omgdendi.blps.dto.EssayToGetDTO;
import com.omgdendi.blps.service.EssayService;
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

    @PostMapping
    public ResponseEntity<EssayDTO> createEssay(@RequestBody EssayDTO essay) {
        return ResponseEntity.ok(essayService.createEssay(essay));
    }

    @GetMapping
    public ResponseEntity<EssayToGetDTO> getEssay(@RequestParam Long essayId) {
        return ResponseEntity.ok(essayService.getEssay(essayId));
    }

    @GetMapping("/title")
    public ResponseEntity<List<EssayToGetDTO>> getEssaysByTitle(@RequestParam String title) {
        return ResponseEntity.ok(essayService.getEssaysByTitle(title));
    }

    @GetMapping("/category")
    public ResponseEntity<List<EssayToGetDTO>> getEssaysByCategory(@RequestParam Long categoryId) {
        return ResponseEntity.ok(essayService.getEssaysByCategory(categoryId));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<EssayToGetDTO>> getRecentEssays(int count) {
        return ResponseEntity.ok(essayService.getRecentEssays(count));
    }
}
