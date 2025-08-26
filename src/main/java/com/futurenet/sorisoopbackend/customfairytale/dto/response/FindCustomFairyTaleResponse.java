package com.futurenet.sorisoopbackend.customfairytale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindCustomFairyTaleResponse {
    private Long id;
    private String title;
    private String thumbnailImage;
    private String categoryName;
    private int pageCount;
    private String author;
}
