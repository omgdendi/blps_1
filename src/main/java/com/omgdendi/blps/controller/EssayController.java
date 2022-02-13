package com.omgdendi.blps.controller;

import com.omgdendi.blps.dto.EssayDto;
import com.omgdendi.blps.exception.CategoryNotFoundException;
import com.omgdendi.blps.service.EssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/essay")
public class EssayController {
    @Autowired
    private EssayService essayService;

    @PostMapping
    public ResponseEntity createEssay(@RequestBody EssayDto essay) {
        try {
            return ResponseEntity.ok(essayService.createEssay(essay));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity getEssaysByTitle(@RequestParam String title) {
        try {
            return ResponseEntity.ok(essayService.getEssaysByTitle(title));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
