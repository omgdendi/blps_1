package com.omgdendi.blps.config;

import com.omgdendi.blps.entity.PrivilegeEntity;
import com.omgdendi.blps.entity.RoleEntity;
import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.entity.types.PrivilegeType;
import com.omgdendi.blps.entity.types.RoleType;
import com.omgdendi.blps.repository.PrivilegeRepo;
import com.omgdendi.blps.repository.RoleRepo;
import com.omgdendi.blps.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupUserLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup;
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PrivilegeRepo privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SetupUserLoader(UserRepo userRepository, RoleRepo roleRepository, PrivilegeRepo privilegeRepository, PasswordEncoder passwordEncoder) {
        this.alreadySetup = false;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        PrivilegeEntity readPrivilege = this.createPrivilegeIfNotFound(PrivilegeType.read.toString());
        PrivilegeEntity writePrivilege = this.createPrivilegeIfNotFound(PrivilegeType.write.toString());

        List<PrivilegeEntity> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound(RoleType.admin.toString(), adminPrivileges);
        createRoleIfNotFound(RoleType.moderator.toString(), adminPrivileges);
        createRoleIfNotFound(RoleType.user.toString(), Arrays.asList(readPrivilege));
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
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);
    }

    //    @Transactional
    PrivilegeEntity createPrivilegeIfNotFound(String name) {
        PrivilegeEntity privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new PrivilegeEntity(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    //    @Transactional
    RoleEntity createRoleIfNotFound(String name, Collection<PrivilegeEntity> privileges) {
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name, privileges);
            roleRepository.save(role);
        }
        return role;
    }
}