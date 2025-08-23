package com.futurenet.sorisoopbackend.fairytale.application;


import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleCategoryResponse;

import java.util.List;

public interface FairyTaleService {
    List<FindFairyTaleContentResponse> getFairyTaleContents(Long fairyTaleId);
    List<FairyTaleCategoryResponse> getFairyTaleCategories();
}
