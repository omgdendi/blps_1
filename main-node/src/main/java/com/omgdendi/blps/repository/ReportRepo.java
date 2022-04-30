package com.omgdendi.blps.repository;

import com.omgdendi.blps.entity.ReportEntity;
import com.omgdendi.blps.entity.ReportStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepo extends JpaRepository<ReportEntity, Integer> {
    List<ReportEntity> findAllByStatus(ReportStatusEntity reportStatusEntity);
}
