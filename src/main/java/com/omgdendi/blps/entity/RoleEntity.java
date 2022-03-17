package com.omgdendi.blps.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<UserEntity> users;

    @Override
    public String toString() {
        return "Role{" +
                "id: " + this.getId() + ", " +
                "name: " + name + "}";
    }
}
