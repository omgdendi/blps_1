package com.omgdendi.blps.repository;

import com.omgdendi.blps.entity.RoleEntity;
import com.omgdendi.blps.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    //List<UserEntity> findAllByRole(RoleEntity role);
    List<UserEntity> findAll();
    void saveAndFlush(UserEntity user);
}
