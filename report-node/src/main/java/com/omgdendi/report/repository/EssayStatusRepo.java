package com.omgdendi.report.repository;

import com.omgdendi.report.entity.EssayStatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EssayStatusRepo extends CrudRepository<EssayStatusEntity, Long> {
    EssayStatusEntity findByName(String name);

    void saveAndFlush(EssayStatusEntity statusEntity);
}
