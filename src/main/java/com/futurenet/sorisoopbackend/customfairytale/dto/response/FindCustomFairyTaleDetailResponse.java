package com.futurenet.sorisoopbackend.customfairytale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindCustomFairyTaleDetailResponse {
    private Long id;
    private String title;
    private String thumbnailImage;
    private int pageCount;
    private String categoryName;
    private String author;
}
