package com.omgdendi.blps.controller.rest;


import com.omgdendi.blps.dto.UserDTO;
import com.omgdendi.blps.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Регистрация пользователя")
    @PostMapping
    public ResponseEntity<?> registration(@RequestBody UserDTO user) {
        userService.registration(user);
        return ResponseEntity.ok("Пользователь был успешно сохранен");
    }


    @Operation(summary = "Получить пользователя")
    @GetMapping
    public ResponseEntity<UserDTO> getUser(@RequestParam Integer id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
}
