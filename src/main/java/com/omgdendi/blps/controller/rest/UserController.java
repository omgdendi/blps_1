package com.omgdendi.blps.controller.rest;


import com.omgdendi.blps.dto.UserDTO;
import com.omgdendi.blps.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping
    public ResponseEntity<?> authorizate(@RequestBody UserDTO user) {
        userService.registration(user);
        return ResponseEntity.ok("Пользователь был успешно сохранен");
    }

    @Operation(summary = "Аутентификация пользователя")
    @GetMapping
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userRequest) {
        UserDTO user = userService.getUser(userRequest.getUsername());
        if (user != null)
            if (userRequest.getPassword().equals(user.getPassword()))
                return ResponseEntity.ok(user);
        return ResponseEntity.status(404).body("Неверный логин или пароль");
    }
}
