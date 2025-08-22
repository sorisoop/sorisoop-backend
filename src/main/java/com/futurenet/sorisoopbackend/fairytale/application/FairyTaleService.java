package com.futurenet.sorisoopbackend.fairytale.application;

import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleContentResponse;

import java.util.List;

public interface FairyTaleService {
    List<FindFairyTaleContentResponse> getFairyTaleContents(Long fairyTaleId);
}
