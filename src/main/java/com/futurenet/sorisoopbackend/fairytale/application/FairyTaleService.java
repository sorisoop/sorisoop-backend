package com.futurenet.sorisoopbackend.fairytale.application;

import com.futurenet.sorisoopbackend.fairytale.dto.response.FIndFairyTaleContentResponse;

import java.util.List;

public interface FairyTaleService {
    List<FIndFairyTaleContentResponse> getFairyTaleContents(Long fairyTaleId);
}
