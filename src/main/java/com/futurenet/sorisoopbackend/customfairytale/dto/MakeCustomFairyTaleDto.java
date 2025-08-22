package com.futurenet.sorisoopbackend.customfairytale.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MakeCustomFairyTaleDto {
    private int page;
    private String imageUrl;
    private String content_kr;
    private String content_en;
    private String sceneType;
    private String emotion;
}
