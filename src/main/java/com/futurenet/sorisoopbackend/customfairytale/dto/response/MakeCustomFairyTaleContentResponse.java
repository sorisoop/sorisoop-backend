package com.futurenet.sorisoopbackend.customfairytale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MakeCustomFairyTaleContentResponse {
    private int page;
    private String title;
    private String imageUrl;
    private String script;
}
