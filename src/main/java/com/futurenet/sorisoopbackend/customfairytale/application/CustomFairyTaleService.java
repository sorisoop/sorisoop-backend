package com.futurenet.sorisoopbackend.customfairytale.application;

import com.futurenet.sorisoopbackend.customfairytale.dto.response.FindCustomFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.FindCustomFairyTaleDetailResponse;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.FindCustomFairyTaleResponse;

import java.util.List;

public interface CustomFairyTaleService {
    List<FindCustomFairyTaleResponse> getAllCustomFairyTaleByCategoryId(int categoryId, Long profileId);
    List<FindCustomFairyTaleContentResponse> getCustomFairyTaleContents(Long customFairyTaleId, Long profileId);
    FindCustomFairyTaleDetailResponse getCustomFairyTaleDetail(Long customFairyTaleId, Long profileId);
}
