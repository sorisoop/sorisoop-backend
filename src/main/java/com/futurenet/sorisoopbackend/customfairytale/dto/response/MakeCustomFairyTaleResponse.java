package com.futurenet.sorisoopbackend.customfairytale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MakeCustomFairyTaleResponse {
    private int page;
    private String imageUrl;
    private String content;
}
