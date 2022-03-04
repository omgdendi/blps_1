package com.omgdendi.blps.service;

import com.omgdendi.blps.dto.UserDTO;
import com.omgdendi.blps.dto.req.RegistrationReqDto;
import com.omgdendi.blps.entity.RoleEntity;
import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.exception.UserAlreadyExistException;
import com.omgdendi.blps.mappers.UserMapper;
import com.omgdendi.blps.repository.RoleRepo;
import com.omgdendi.blps.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity registration(RegistrationReqDto registrationReqDto) throws UserAlreadyExistException {
        String usernameFromDto = registrationReqDto.getUsername();

        if (userRepo.findByUsername(usernameFromDto).get() == null) {
            UserEntity user = new UserEntity();
            user.setUsername(registrationReqDto.getUsername());
            user.setPassword(passwordEncoder.encode(registrationReqDto.getPassword()));
            RoleEntity roleUser = roleRepo.findByName("ROLE_USER");
            List<RoleEntity> userRoles = new ArrayList<>();
            userRoles.add(roleUser);

            user.setRoles(userRoles);

            UserEntity registeredUser = userRepo.save(user);
            log.info("IN register - user: {} successfully registered", registeredUser);

            return registeredUser;
        } else {
            log.error("User with username: " + usernameFromDto + " already exists");
            throw new UserAlreadyExistException();
        }
    }



    public UserEntity findByUsername(String username) {
        UserEntity result = userRepo.findByUsername(username).get();
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }
}
