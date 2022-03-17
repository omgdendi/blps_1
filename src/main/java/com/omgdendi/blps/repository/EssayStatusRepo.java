package com.omgdendi.blps.repository;

import com.omgdendi.blps.entity.EssayStatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EssayStatusRepo extends CrudRepository<EssayStatusRepo, Long> {
    EssayStatusEntity findByName(String name);
}
