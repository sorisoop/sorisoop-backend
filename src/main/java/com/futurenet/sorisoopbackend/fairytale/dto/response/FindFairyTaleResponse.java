package com.futurenet.sorisoopbackend.fairytale.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindFairyTaleResponse {
    private Long id;
    private String title;
    private String author;
    private String thumbnailImage;
    private int pageCount;
    private String name; // 카테고리 이름
    @JsonProperty("isFavorite")
    private boolean favorite; // 찜 상태 T: 찜O, F: 찜X
}
