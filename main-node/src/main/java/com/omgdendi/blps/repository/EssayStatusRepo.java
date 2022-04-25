package com.omgdendi.blps.repository;

import com.omgdendi.blps.entity.EssayStatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EssayStatusRepo extends CrudRepository<EssayStatusEntity, Long> {
    EssayStatusEntity findByName(String name);

    void saveAndFlush(EssayStatusEntity statusEntity);
}
