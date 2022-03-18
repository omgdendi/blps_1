package com.omgdendi.blps.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity extends BaseEntity {
    @Column(name = "name")
    private String name;


    @Override
    public String toString() {
        return "Role{" +
                "id: " + this.getId() + ", " +
                "name: " + name + "}";
    }
}
