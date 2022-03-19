package com.omgdendi.blps.config;

import com.omgdendi.blps.entity.EssayStatusEntity;
import com.omgdendi.blps.entity.RoleEntity;
import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.entity.types.EssayStatus;
import com.omgdendi.blps.entity.types.RoleType;
import com.omgdendi.blps.repository.EssayStatusRepo;
import com.omgdendi.blps.repository.RoleRepo;
import com.omgdendi.blps.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class SetupUserLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final String adminUsername = "admin";
    private final String adminPassword = "admin";

    boolean alreadySetup;
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final EssayStatusRepo statusRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SetupUserLoader(UserRepo userRepository, RoleRepo roleRepository, PasswordEncoder passwordEncoder, EssayStatusRepo statusRepository) {
        this.alreadySetup = false;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.statusRepository = statusRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {


        if (alreadySetup) return;

        createStatusIfNotFound(EssayStatus.approved.toString());
        createStatusIfNotFound(EssayStatus.not_approved.toString());
        createStatusIfNotFound(EssayStatus.failed.toString());
        createStatusIfNotFound(EssayStatus.checking.toString());

        createRoleIfNotFound(RoleType.admin.toString());
        createRoleIfNotFound(RoleType.moderator.toString());
        createRoleIfNotFound(RoleType.user.toString());
        this.createAdminUser(adminUsername, adminPassword);
        alreadySetup = true;

    }

    void createAdminUser(String username, String password) {
        RoleEntity adminRole = roleRepository.findByName(RoleType.admin.toString());
        if (userRepository.findByUsername(username).isPresent()) return;

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.addRole(adminRole);
        userRepository.saveAndFlush(user);
    }


    RoleEntity createRoleIfNotFound(String name) {
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            roleRepository.saveAndFlush(role);
        }
        return role;
    }

    EssayStatusEntity createStatusIfNotFound(String name) {
        EssayStatusEntity statusEntity = statusRepository.findByName(name);
        if (statusEntity == null) {
            statusEntity = new EssayStatusEntity(name);
            statusRepository.saveAndFlush(statusEntity);
        }
        return statusEntity;
    }
}