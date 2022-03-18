package com.omgdendi.blps.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<PrivilegeEntity> privileges;

    @Override
    public String toString() {
        return "Role{" +
                "id: " + this.getId() + ", " +
                "name: " + name + "}";
    }

    public RoleEntity(String name, Collection<PrivilegeEntity> privileges) {
        this.name = name;
        this.privileges = privileges;
    }

    public RoleEntity() {
    }
}
