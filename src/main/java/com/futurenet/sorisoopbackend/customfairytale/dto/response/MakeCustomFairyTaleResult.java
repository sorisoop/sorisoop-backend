package com.futurenet.sorisoopbackend.customfairytale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MakeCustomFairyTaleResult {
    private Long customFairyTaleId;
    private List<MakeCustomFairyTaleContentResponse> pages;
}
