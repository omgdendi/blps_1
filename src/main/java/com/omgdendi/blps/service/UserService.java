package com.omgdendi.blps.service;

import com.omgdendi.blps.dto.UserDTO;
import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.exception.UserAlreadyExistException;
import com.omgdendi.blps.mappers.UserMapper;
import com.omgdendi.blps.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserEntity registration(UserEntity user) throws UserAlreadyExistException {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException();
        }
        return userRepo.save(user);
    }

    public UserDTO getUser(Long id) {
        return UserMapper.INSTANCE.toDTO(userRepo.findById(id).get());
    }
}
