package com.omgdendi.blps.dto.res;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EssayResDto {
    private Integer id;
    private String title;
    private String author;
    private String content;
    private Date dateLoad;
    private String category;
}
