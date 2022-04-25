package com.omgdendi.blps.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.omgdendi.blps.service.KafkaService;
import com.omgdendi.data.dto.common.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping
    public void sendOrder(String msg) throws JsonProcessingException {
        ReportDto reportDto = new ReportDto();
        reportDto.setEssayId(1);
        kafkaService.send(reportDto);
    }
}
