package com.futurenet.sorisoopbackend.fairytale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FairyTaleResponse {
    private Long id;
    private String title;
    private String author;
    private String thumbnailImage;
    private int pageCount;
    private String name; // 카테고리 명
}
