package com.omgdendi.blps.controller.rest;

import com.omgdendi.blps.dto.req.EssayReqDto;
import com.omgdendi.blps.dto.res.EssayResDto;
import com.omgdendi.blps.service.EssayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/essay")
public class EssayController {

    private final EssayService essayService;

    @Autowired
    public EssayController(EssayService essayService) {
        this.essayService = essayService;
    }


    @RolesAllowed("ROLE_USER")
    @Operation(summary = "Отправить запрос на подтверждение создания письменного материала")
    @PostMapping("/create")
    public ResponseEntity<EssayResDto> createEssay(@RequestBody EssayReqDto essay) {
        return ResponseEntity.ok(essayService.sendEssayToCheck(essay));
    }

    @Operation(summary = "Получить письменный материал по указанному id")
    @GetMapping("/{id}")
    public ResponseEntity<EssayResDto> getEssay(@Parameter(description = "id письменного материла")
                                                      @PathVariable int id) {
        return ResponseEntity.ok(essayService.getEssay(id));
    }

    @Operation(summary = "Получить все письменные материала по совпадению с указанным заголовком")
    @GetMapping("/title")
    public ResponseEntity<List<EssayResDto>> getEssaysByTitle(
            @Parameter(description = "заголовок письменного материла")
            @RequestParam String title) {
        return ResponseEntity.ok(essayService.getEssaysByTitle(title));
    }

    @Operation(summary = "Получить все письменные материалы по указанной категории")
    @GetMapping("/category/{id}")
    public ResponseEntity<List<EssayResDto>> getEssaysByCategory(
            @Parameter(description = "id категории письменного материла")
            @PathVariable int id) {
        return ResponseEntity.ok(essayService.getEssaysByCategory(id));
    }

    @Operation(summary = "Получить определенное количество письменныех материалов, " +
            "отсортированных по дате загрузки (от более ранних до более поздних)")
    @GetMapping("/recent/{amount}")
    public ResponseEntity<List<EssayResDto>> getRecentEssays(@Parameter(description = "количество письменного материла")
                                                                             int amount) {
        return ResponseEntity.ok(essayService.getRecentEssays(amount));
    }


}
