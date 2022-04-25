package com.omgdendi.report.config;


import com.omgdendi.report.entity.*;
import com.omgdendi.report.repository.*;
import com.omgdendi.report.types.EssayStatus;
import com.omgdendi.report.types.PrivilegeType;
import com.omgdendi.report.types.ReportStatus;
import com.omgdendi.report.types.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Component
public class SetupUserLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final String adminUsername = "admin";
    private final String adminPassword = "admin";

    boolean alreadySetup;
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PrivilegeRepo privilegeRepository;
    private final EssayStatusRepo statusRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReportStatusRepo reportStatusRepo;

    @Autowired
    public SetupUserLoader(UserRepo userRepository, RoleRepo roleRepository, EssayStatusRepo statusRepository, PasswordEncoder passwordEncoder, PrivilegeRepo privilegeRepository, ReportStatusRepo reportStatusRepo) {
        this.alreadySetup = false;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.statusRepository = statusRepository;
        this.privilegeRepository = privilegeRepository;
        this.reportStatusRepo = reportStatusRepo;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        log.error("!!! setup loader");
        if (alreadySetup) return;

        this.createStatusIfNotFound(EssayStatus.NOT_APPROVED);
        this.createStatusIfNotFound(EssayStatus.APPROVED);
        this.createStatusIfNotFound(EssayStatus.FAILED);
        this.createStatusIfNotFound(EssayStatus.CHECKING);
        this.createReportStatusIfNotFound(ReportStatus.ACTIVE);
        this.createReportStatusIfNotFound(ReportStatus.NOT_ACTIVE);

        PrivilegeEntity readPrivilege = this.createPrivilegeIfNotFound(PrivilegeType.READ);
        PrivilegeEntity writePrivilege = this.createPrivilegeIfNotFound(PrivilegeType.WRITE_APPROVED);
        PrivilegeEntity writeNotApprovedPrivilege = this.createPrivilegeIfNotFound(PrivilegeType.WRITE_NOT_APPROVED);

        this.createRoleIfNotFound(RoleType.ADMIN, Arrays.asList(
                writePrivilege,
                readPrivilege
        ));
        this.createRoleIfNotFound(RoleType.MODERATOR, Arrays.asList(
                writePrivilege,
                readPrivilege
        ));
        this.createRoleIfNotFound(RoleType.USER, Arrays.asList(
                readPrivilege,
                writeNotApprovedPrivilege
        ));
        this.createAdminUser(adminUsername, adminPassword);
        alreadySetup = true;

    }

    @Transactional
    void createAdminUser(String username, String password) {
        RoleEntity adminRole = roleRepository.findByName(RoleType.ADMIN);
        if (userRepository.findByUsername(username).isPresent()) return;
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.addRole(adminRole);
        userRepository.saveAndFlush(user);
    }

    @Transactional
    RoleEntity createRoleIfNotFound(String name, Collection<PrivilegeEntity> privileges) {
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    PrivilegeEntity createPrivilegeIfNotFound(String name) {
        PrivilegeEntity privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new PrivilegeEntity(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }


    private void createStatusIfNotFound(String name) {
        EssayStatusEntity statusEntity = statusRepository.findByName(name);
        if (statusEntity == null) {
            statusEntity = new EssayStatusEntity(name);
            statusRepository.saveAndFlush(statusEntity);
        }
    }

    private void createReportStatusIfNotFound(String name) {
        ReportStatusEntity reportStatusEntity = reportStatusRepo.findByName(name);
        if (reportStatusEntity == null) {
            reportStatusEntity = new ReportStatusEntity(name);
            reportStatusRepo.saveAndFlush(reportStatusEntity);
        }
    }
}