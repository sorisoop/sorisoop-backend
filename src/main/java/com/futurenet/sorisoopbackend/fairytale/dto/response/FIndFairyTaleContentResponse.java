package com.futurenet.sorisoopbackend.fairytale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FIndFairyTaleContentResponse {
    private Long id;
    private int page;
    private String imageUrl;
    private String script;
}
