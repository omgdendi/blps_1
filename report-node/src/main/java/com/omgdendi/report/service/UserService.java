package com.omgdendi.report.service;

import com.omgdendi.data.dto.req.RegistrationReqDto;
import com.omgdendi.data.dto.res.NotificationResDto;
import com.omgdendi.report.entity.NotificationEntity;
import com.omgdendi.report.entity.UserEntity;
import com.omgdendi.report.exception.UserAlreadyExistException;
import com.omgdendi.report.mappers.NotificationResMapper;
import com.omgdendi.report.repository.NotificationRepo;
import com.omgdendi.report.repository.RoleRepo;
import com.omgdendi.report.repository.UserRepo;
import com.omgdendi.report.types.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final NotificationRepo notificationRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Autowired
    public UserService(UserRepo userRepo, NotificationRepo notificationRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.notificationRepo = notificationRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
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
