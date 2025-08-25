package com.futurenet.sorisoopbackend.fairytale.application;


import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleCategoryResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleResponse;

import java.util.List;

public interface FairyTaleService {
    List<FindFairyTaleContentResponse> getFairyTaleContents(Long fairyTaleId);
    List<FindFairyTaleCategoryResponse> getFairyTaleCategories();
    List<FindFairyTaleResponse> getFairyTaleList(Long categoryId, int page, Long profileId);
    List<FindFairyTaleResponse> searchFairyTaleList(String keyword, int page, Long profileId);
    FindFairyTaleResponse getFairyTaleDetail(Long fairyTaleId, Long profileId);
    List<FindFairyTaleResponse> getFairyTalesRandom(Long profileId);
}
