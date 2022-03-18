package com.omgdendi.blps.controller.rest;

import com.omgdendi.blps.entity.RoleEntity;
import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.entity.types.RoleType;
import com.omgdendi.blps.exception.RoleModeratorException;
import com.omgdendi.blps.repository.RoleRepo;
import com.omgdendi.blps.repository.UserRepo;
import com.omgdendi.blps.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public ResponseEntity assignModerator(@PathVariable String username) throws RoleModeratorException {
        System.out.println("sad");
        UserEntity user = userService.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("User with username: " + username + " not found");

        RoleEntity moderatorRole = this.roleRepo.findByName(RoleType.moderator.toString());
        Collection<RoleEntity> userRoles = user.getRoles();

        if (userRoles.contains(moderatorRole))
            throw new RoleModeratorException("User with username: " + username + " already is moderator");

        userRoles.add(moderatorRole);
        user.setRoles(userRoles);
        userRepo.save(user);
        return ResponseEntity.ok("the moderator role was assigned to user with username: " + username);
    }

    @Operation(summary = "Кикнуть из модераторов")
    @DeleteMapping("/moderator/{username}")
    public ResponseEntity retrieveModerator(@PathVariable String username) throws RoleModeratorException {
        UserEntity user = userService.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("User with username: " + username + " not found");

        RoleEntity moderatorRole = this.roleRepo.findByName(RoleType.moderator.toString());
        Collection<RoleEntity> userRoles = user.getRoles();

        if (!userRoles.contains(moderatorRole))
            throw new RoleModeratorException("User with username: " + username + " not assigned as moderator");

        userRoles.remove(moderatorRole);
        user.setRoles(userRoles);
        userRepo.save(user);
        return ResponseEntity.ok("The moderator role was retrieved to user with username: " + username);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RoleModeratorException.class, UsernameNotFoundException.class})
    public String handleException(RuntimeException e) {
        return e.getMessage();
    }
}
