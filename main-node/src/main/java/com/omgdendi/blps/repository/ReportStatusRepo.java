package com.omgdendi.blps.repository;

import com.omgdendi.blps.entity.ReportStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportStatusRepo extends JpaRepository<ReportStatusEntity, Integer> {
    ReportStatusEntity findByName(String name);
}
