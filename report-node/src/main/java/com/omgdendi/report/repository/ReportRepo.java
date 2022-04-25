package com.omgdendi.report.repository;

import com.omgdendi.report.entity.ReportEntity;
import com.omgdendi.report.entity.ReportStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepo extends JpaRepository<ReportEntity, Integer> {
    List<ReportEntity> findAllByStatus(ReportStatusEntity reportStatusEntity);
}
