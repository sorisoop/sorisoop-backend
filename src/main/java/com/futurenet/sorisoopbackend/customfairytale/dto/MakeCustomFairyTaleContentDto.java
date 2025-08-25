package com.futurenet.sorisoopbackend.customfairytale.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MakeCustomFairyTaleContentDto {
    private int page;
    private String imageUrl;
    private String contentKr;
    private String contentEn;
    private String sceneType;
    private String emotion;
}
