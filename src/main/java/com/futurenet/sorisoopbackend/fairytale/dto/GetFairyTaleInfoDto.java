package com.futurenet.sorisoopbackend.fairytale.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetFairyTaleInfoDto {
    private int pageCount;
    private String title;
    private String thumbnailImage;
}
