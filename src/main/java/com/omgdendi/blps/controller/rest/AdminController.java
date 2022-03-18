package com.omgdendi.blps.controller.rest;

import com.omgdendi.blps.entity.RoleEntity;
import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.entity.types.RoleType;
import com.omgdendi.blps.repository.RoleRepo;
import com.omgdendi.blps.repository.UserRepo;
import com.omgdendi.blps.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    private final UserService userService;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    public AdminController(UserService userService, RoleRepo roleRepo, UserRepo userRepo) {
        this.userService = userService;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
    }

    @Operation(summary = "Выдать роль модератора")
    @PostMapping("/moderator/{username}")
    public ResponseEntity assignModerator(@PathVariable String username) {
        UserEntity user = userService.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("User with username: " + username + " not found");

        Collection<RoleEntity> currentRoles = user.getRoles();
        currentRoles.add(roleRepo.findByName(RoleType.moderator.toString()));
        user.setRoles(currentRoles);
        userRepo.save(user);
        return ResponseEntity.ok("the moderator role was assigned to user with username: " + username );
    }

    //todo не работает
    @Operation(summary = "Кикнуть из модераторов")
    @DeleteMapping("/moderator/{username}")
    public ResponseEntity retrieveModerator(@PathVariable String username) {
        UserEntity user = userService.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User with username: " + username + " not found");

        Collection<RoleEntity> currentRoles = user.getRoles();
        if (!currentRoles.contains(roleRepo.findByName(RoleType.moderator.toString())))
            throw new UsernameNotFoundException("User with username: " + username + " not assigned as moderator");

        user.setRoles(currentRoles.stream()
                .filter(roleEntity -> !roleEntity.toString().equals(RoleType.moderator.toString()))
                .collect(Collectors.toList()));

        userRepo.save(user);
        return ResponseEntity.ok("The moderator role was retrieved to user with username: " + username );
    }
}
