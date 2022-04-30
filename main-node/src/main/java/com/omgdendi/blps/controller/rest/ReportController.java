package com.omgdendi.blps.controller.rest;

import com.omgdendi.blps.service.EssayService;
import com.omgdendi.blps.service.ReportService;
import com.omgdendi.data.dto.res.ReportResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final EssayService essayService;
    private final ReportService reportService;

    @Autowired
    public ReportController(EssayService essayService, ReportService reportService) {
        this.essayService = essayService;
        this.reportService = reportService;
    }

    @Operation(summary = "Получить список всех заявок")
    @GetMapping
    public List<ReportResDto> getAllReports() {
        return reportService.getAllReports();
    }

    @Operation(summary = "Получить список активных заявок")
    @GetMapping("/active")
    public List<ReportResDto> getActiveReports() {
        return reportService.getActiveReports();
    }

    @Operation(summary = "Рассмотреть заявку и установить статус 'Принят' письменному материалу")
    @PutMapping("/status/pass/{id}")
    public ResponseEntity<?> setPassStatusToEssay(@Parameter(description = "id заявки")
                                                  @PathVariable int id) {
        essayService.setPassStatusToEssay(id);
        return ResponseEntity.ok("Статус успешно был обновлен на 'Принят'");
    }

    @Operation(summary = "Рассмотреть заявку и установить статус 'Не принят' письменному материалу")
    @PutMapping("/essay/status/fail/{id}")
    public ResponseEntity<?> setFailStatusToEssay(@Parameter(description = "id заявки")
                                                  @PathVariable int id) {
        essayService.setFailStatusToEssay(id);
        return ResponseEntity.ok("Статус успешно был обновлен на 'Не принят'");
    }
}
