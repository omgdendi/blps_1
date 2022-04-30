package com.omgdendi.report.repository;

import com.omgdendi.report.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findAll();
    void saveAndFlush(UserEntity user);
}
