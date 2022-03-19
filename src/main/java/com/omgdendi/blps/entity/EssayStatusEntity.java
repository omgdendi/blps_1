package com.omgdendi.blps.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "essay_status")
@Data
public class EssayStatusEntity extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<EssayEntity> essay = new ArrayList<EssayEntity>();

    public EssayStatusEntity(String name) {
        this.name = name;
    }

    public EssayStatusEntity() {
    }
}
