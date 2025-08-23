package com.futurenet.sorisoopbackend.fairytale.application;


import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleCategoryResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleContentResponse;

import java.util.List;

public interface FairyTaleService {
    List<FindFairyTaleContentResponse> getFairyTaleContents(Long fairyTaleId);
    List<FairyTaleCategoryResponse> getFairyTaleCategories();
    List<FairyTaleResponse> getFairyTaleList(Long categoryId, int page);
}
