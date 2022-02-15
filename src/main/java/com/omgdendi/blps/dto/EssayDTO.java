package com.omgdendi.blps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EssayDTO {
    @NotNull
    private String title;
    private String author;
    @NotNull
    private String content;
    private Long userId;
    private String categoryName;
}
