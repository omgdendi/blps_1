package com.omgdendi.blps.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user_entity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EssayEntity> essays = new ArrayList<EssayEntity>();
}
