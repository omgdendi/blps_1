package com.omgdendi.blps.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report_status")
public class ReportStatusEntity {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    public ReportStatusEntity(String name) {
        this.name = name;
    }
}
