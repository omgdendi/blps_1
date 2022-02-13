package com.omgdendi.blps.dto;

import com.omgdendi.blps.entity.EssayEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EssayDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private Date dateCreate;
    private Long userId;
    private String categoryName;
}
