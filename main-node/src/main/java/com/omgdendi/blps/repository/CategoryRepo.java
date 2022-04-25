package com.omgdendi.blps.repository;


import com.omgdendi.blps.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends CrudRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByName(String name);
    Optional<CategoryEntity> findById(Long id);
    List<CategoryEntity> findAll();
}
