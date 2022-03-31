package com.omgdendi.blps.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EssayReqDto {
    @NotNull
    private String title;
    private String author;
    @NotNull
    private String content;
    private String categoryName;
}
