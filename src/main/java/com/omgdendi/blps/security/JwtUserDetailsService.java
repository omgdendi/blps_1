package com.omgdendi.blps.security;

import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.security.jwt.JwtUser;
import com.omgdendi.blps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findByUsername(username);
        return JwtUser.fromUserToJwtUser(user);
    }

}