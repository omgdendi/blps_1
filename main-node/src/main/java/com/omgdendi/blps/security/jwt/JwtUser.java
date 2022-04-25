package com.omgdendi.blps.security.jwt;

import com.omgdendi.blps.entity.RoleEntity;
import com.omgdendi.blps.entity.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class JwtUser implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static JwtUser fromUserToJwtUser(UserEntity user) {
        JwtUser jwtUser = new JwtUser();
        jwtUser.username = user.getUsername();
        jwtUser.password = user.getPassword();
        jwtUser.grantedAuthorities = mapToGrantedAuthorities(user.getRoles());
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

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<RoleEntity> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }

//    private static Collection<? extends GrantedAuthority> getAuthorities(Collection<RoleEntity> roles) {
//        return getGrantedAuthorities(getPrivileges(roles));
//    }
//
//    private static List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String privilege : privileges) {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        }
//        return authorities;
//    }
//
//    private static List<String> getPrivileges(Collection<RoleEntity> roles) {
//        List<String> privileges = new ArrayList<>();
//        List<PrivilegeEntity> collection = new ArrayList<>();
//        for (RoleEntity role : roles) {
//            privileges.add(role.getName());
//            collection.addAll(role.getPrivileges());
//        }
//        for (PrivilegeEntity item : collection) {
//            privileges.add(item.getName());
//        }
//        return privileges;
//    }
}
