package com.omgdendi.blps.config;

import com.omgdendi.blps.entity.RoleEntity;
import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.entity.types.RoleType;
import com.omgdendi.blps.repository.RoleRepo;
import com.omgdendi.blps.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class SetupUserLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup;
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SetupUserLoader(UserRepo userRepository, RoleRepo roleRepository, PasswordEncoder passwordEncoder) {
        this.alreadySetup = false;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        createRoleIfNotFound(RoleType.admin.toString());
        createRoleIfNotFound(RoleType.moderator.toString());
        createRoleIfNotFound(RoleType.user.toString());
        this.createAdminUser("admin", "admin");
        alreadySetup = true;
    }

    //    @Transactional
    void createAdminUser(String username, String password) {
        RoleEntity adminRole = roleRepository.findByName(RoleType.admin.toString());
        if (userRepository.findByUsername(username).isPresent()) return;

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.addRole(adminRole);
        userRepository.save(user);
    }


    //    @Transactional
    RoleEntity createRoleIfNotFound(String name) {
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            roleRepository.save(role);
        }
        return role;
    }
}