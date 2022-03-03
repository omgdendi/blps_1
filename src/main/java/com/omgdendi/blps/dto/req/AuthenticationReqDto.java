package com.omgdendi.blps.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AuthenticationReqDto {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 1, message = "Min size of username: 1")
    @Size(max = 30, message = "Max size of username: 30")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3, message = "Min size of password: 3")
    private String password;
}