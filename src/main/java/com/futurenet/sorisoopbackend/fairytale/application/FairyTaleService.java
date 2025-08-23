package com.futurenet.sorisoopbackend.fairytale.application;

import com.futurenet.sorisoopbackend.fairytale.dto.response.FIndFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleCategoryResponse;

import java.util.List;

public interface FairyTaleService {
    List<FIndFairyTaleContentResponse> getFairyTaleContents(Long fairyTaleId);
    List<FairyTaleCategoryResponse> getFairyTaleCategories();
}
