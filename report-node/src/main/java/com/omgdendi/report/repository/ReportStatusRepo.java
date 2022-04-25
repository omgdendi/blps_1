package com.omgdendi.report.repository;

import com.omgdendi.report.entity.ReportStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportStatusRepo extends JpaRepository<ReportStatusEntity, Integer> {
    ReportStatusEntity findByName(String name);
}
