package com.omgdendi.blps.repository;

import com.omgdendi.blps.entity.EssayEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EssayRepo extends CrudRepository<EssayEntity, Long> {

    @Query(value = "SELECT * FROM essay WHERE title ~ ?1", nativeQuery = true)
    List<EssayEntity> findAllByTitle(String title);
}
