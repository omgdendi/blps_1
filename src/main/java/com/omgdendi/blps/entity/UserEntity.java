package com.omgdendi.blps.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "user_entity")
public class UserEntity extends BaseEntity {
    @NotNull
    @Column(unique=true)
    private String username;
    @NotNull
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EssayEntity> essays = new ArrayList<EssayEntity>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();
}
