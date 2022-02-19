package com.omgdendi.blps.repository;

import com.omgdendi.blps.entity.EssayEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EssayRepo extends CrudRepository<EssayEntity, Integer> {

    @Query(value = "SELECT * FROM essay WHERE title ~ ?1", nativeQuery = true)
    List<EssayEntity> findAllByTitle(String title);

    @Query(value = "SELECT * FROM essay WHERE category_id = ?1", nativeQuery = true)
    List<EssayEntity> findAllByCategory(Integer id);

    @Query(value = "SELECT * FROM essay ORDER BY date_load DESC LIMIT ?1", nativeQuery = true)
    List<EssayEntity> findAllRecentEssays(int count);
}
