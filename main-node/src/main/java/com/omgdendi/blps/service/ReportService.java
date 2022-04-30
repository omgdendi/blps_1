package com.omgdendi.blps.service;

import com.omgdendi.blps.entity.EssayEntity;
import com.omgdendi.blps.entity.ReportEntity;
import com.omgdendi.blps.mappers.EssayResMapper;
import com.omgdendi.blps.repository.EssayRepo;
import com.omgdendi.blps.repository.ReportRepo;
import com.omgdendi.blps.repository.ReportStatusRepo;
import com.omgdendi.blps.types.ReportStatus;
import com.omgdendi.data.dto.common.ReportDto;
import com.omgdendi.data.dto.res.ReportResDto;
import com.omgdendi.blps.entity.ReportStatusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private ReportRepo reportRepo;
    private EssayRepo essayRepo;
    private ReportStatusRepo reportStatusRepo;

    @Autowired
    public ReportService(ReportRepo reportRepo, EssayRepo essayRepo, ReportStatusRepo reportStatusRepo) {
        this.reportRepo = reportRepo;
        this.essayRepo = essayRepo;
        this.reportStatusRepo = reportStatusRepo;
    }

    public void saveNewReport(ReportDto reportDto) {
        EssayEntity essayEntity = essayRepo.findById(reportDto.getEssayId()).get();
        ReportStatusEntity reportStatusEntity = reportStatusRepo.findByName(ReportStatus.ACTIVE);
        ReportEntity report = new ReportEntity();
        report.setEssay(essayEntity);
        report.setStatus(reportStatusEntity);
        reportRepo.save(report);
    }

    public ReportEntity findById(Integer id) {
        ReportEntity report = reportRepo.findById(id).get();
        return report;
    }

    @Transactional
    public void setNotActiveStatusToReport(ReportEntity report) {
        ReportStatusEntity status = reportStatusRepo.findByName(ReportStatus.NOT_ACTIVE);
        report.setStatus(status);
        reportRepo.save(report);
    }

    public List<ReportResDto> getAllReports() {
        List<ReportEntity> reports = reportRepo.findAll();
        return reports.stream().map(report -> this.castToReportResDto(report)).collect(Collectors.toList());
    }

    public List<ReportResDto> getActiveReports() {
        ReportStatusEntity status = reportStatusRepo.findByName(ReportStatus.ACTIVE);
        List<ReportEntity> reports = reportRepo.findAllByStatus(status);
        return reports.stream().map(report -> this.castToReportResDto(report)).collect(Collectors.toList());
    }

    private ReportResDto castToReportResDto(ReportEntity report) {
        ReportResDto reportResDto = new ReportResDto();
        reportResDto.setId(report.getId());
        reportResDto.setEssayResDto(EssayResMapper.INSTANCE.toDTO(report.getEssay()));
        return reportResDto;
    }
}
