package com.omgdendi.blps.security.jwt;

import com.omgdendi.blps.entity.PrivilegeEntity;
import com.omgdendi.blps.entity.RoleEntity;
import com.omgdendi.blps.entity.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class JwtUser implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static JwtUser fromUserToJwtUser(UserEntity user, Collection<? extends GrantedAuthority> grantedAuthorities) {
        JwtUser jwtUser = new JwtUser();
        jwtUser.username = user.getUsername();
        jwtUser.password = user.getPassword();
        jwtUser.grantedAuthorities = grantedAuthorities;
        System.out.println("jwt");
        System.out.println(jwtUser.getGrantedAuthorities());
        return jwtUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
