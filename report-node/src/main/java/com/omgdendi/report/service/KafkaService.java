package com.omgdendi.report.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgdendi.data.dto.common.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    private final ObjectMapper objectMapper;
    private final ReportService reportService;

    @Autowired
    public KafkaService(ObjectMapper objectMapper, ReportService reportService) {
        this.objectMapper = objectMapper;
        this.reportService = reportService;
    }

    @KafkaListener(topics="msg")
    public void orderListener(String msg) throws JsonProcessingException {
        ReportDto report = objectMapper.readValue(msg, ReportDto.class);
        reportService.saveNewReport(report);
    }
}
