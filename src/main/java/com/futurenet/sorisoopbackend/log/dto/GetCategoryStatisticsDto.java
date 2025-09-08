package com.futurenet.sorisoopbackend.log.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoryStatisticsDto {
    private int categoryId;
    private String categoryName;
    private int readCount;
}
