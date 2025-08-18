package com.futurenet.sorisoopbackend.customfairytale.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MakeCustomFairyTaleRequest {
    private String imageUrl;
    private String imageContentType;
}
