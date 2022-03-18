package com.omgdendi.blps.security;

import com.omgdendi.blps.entity.PrivilegeEntity;
import com.omgdendi.blps.entity.RoleEntity;
import com.omgdendi.blps.entity.UserEntity;
import com.omgdendi.blps.repository.RoleRepo;
import com.omgdendi.blps.security.jwt.JwtUser;
import com.omgdendi.blps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final RoleRepo roleRepository;
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(RoleRepo roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("load JwtUserDetailsService");
        UserEntity user = userService.findByUsername(username);
        return JwtUser.fromUserToJwtUser(user, getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<RoleEntity> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<RoleEntity> roles) {
        List<String> privileges = new ArrayList<>();
        List<PrivilegeEntity> collection = new ArrayList<>();
        System.out.println("roles");
        System.out.println(roles);
        for (RoleEntity role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (PrivilegeEntity item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}