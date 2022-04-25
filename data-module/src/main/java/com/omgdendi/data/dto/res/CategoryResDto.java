package com.omgdendi.data.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResDto {
    private Long id;
    private String name;
    private int count;
}
