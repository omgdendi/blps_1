package com.omgdendi.blps.controller.rest;


import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.security.jwt.JwtProvider;
import com.omgdendi.blps.service.UserService;
import com.omgdendi.data.dto.req.AuthenticationReqDto;
import com.omgdendi.data.dto.res.AuthenticationResDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }


    @Operation(summary = "Аутентификация пользователя")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResDto> login(@RequestBody @Valid AuthenticationReqDto authenticationReqDto) {
        String username = authenticationReqDto.getUsername();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                authenticationReqDto.getPassword()));

        UserEntity user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        String token = jwtProvider.generateToken(username);
        AuthenticationResDto authenticationResDto = new AuthenticationResDto(username, token);
        return ResponseEntity.ok(authenticationResDto);
    }
}
