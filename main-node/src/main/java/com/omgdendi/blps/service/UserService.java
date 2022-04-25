package com.omgdendi.blps.service;

import com.omgdendi.data.dto.req.RegistrationReqDto;
import com.omgdendi.data.dto.res.NotificationResDto;
import com.omgdendi.blps.entity.NotificationEntity;
import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.exception.UserAlreadyExistException;
import com.omgdendi.blps.mappers.NotificationResMapper;
import com.omgdendi.blps.repository.NotificationRepo;
import com.omgdendi.blps.repository.RoleRepo;
import com.omgdendi.blps.repository.UserRepo;
import com.omgdendi.blps.types.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final NotificationRepo notificationRepo;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder, NotificationRepo notificationRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.notificationRepo = notificationRepo;
    }

    public UserEntity registration(RegistrationReqDto registrationReqDto) throws UserAlreadyExistException {
        String usernameFromDto = registrationReqDto.getUsername();

        if (!userRepo.findByUsername(usernameFromDto).isPresent()) {
            UserEntity user = new UserEntity();
            user.setUsername(registrationReqDto.getUsername());
            user.setPassword(passwordEncoder.encode(registrationReqDto.getPassword()));

            user.addRole(roleRepo.findByName(RoleType.USER));

            UserEntity registeredUser = userRepo.save(user);
            log.info("IN register - user: {} successfully registered", registeredUser);

            return registeredUser;
        } else {
            log.error("User with username: " + usernameFromDto + " already exists");
            throw new UserAlreadyExistException();
        }
    }



    public UserEntity findByUsername(String username) {
        UserEntity result = userRepo.findByUsername(username).orElse(null);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    public List<NotificationResDto> getNotification(String username) {
        UserEntity user = userRepo.findByUsername(username).get();
        List<NotificationEntity> notifications = notificationRepo.findByUser(user);
        return notifications.stream().map(notification -> NotificationResMapper.INSTANCE.toDTO(notification)).collect(Collectors.toList());
    }
}
