package com.omgdendi.blps.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EssayToGetDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private Date dateCreate;
}
