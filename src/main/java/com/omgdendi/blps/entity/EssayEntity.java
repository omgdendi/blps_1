package com.omgdendi.blps.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "essay")
@Data
public class EssayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    @Column(columnDefinition="TEXT")
    private String content;
    private String author;
    @NotNull
    private Date dateLoad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
