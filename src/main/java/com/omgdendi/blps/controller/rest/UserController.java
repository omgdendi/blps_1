package com.omgdendi.blps.controller.rest;


import com.omgdendi.blps.dto.UserDTO;
import com.omgdendi.blps.service.UserService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation (value = "получить", notes = "получить")
    @PostMapping
    public ResponseEntity<?> registration(@RequestBody UserDTO user) {
        userService.registration(user);
        return ResponseEntity.ok("Пользователь был успешно сохранен");
    }
    @ApiOperation (value = "получить", notes = "получить")
    @GetMapping
    public ResponseEntity<UserDTO> getUser(@RequestParam Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
}
