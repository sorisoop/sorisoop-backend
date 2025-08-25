package com.futurenet.sorisoopbackend.customfairytale.application;

import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleErrorCode;
import com.futurenet.sorisoopbackend.customfairytale.application.exception.CustomFairyTaleException;
import com.futurenet.sorisoopbackend.customfairytale.domain.CustomFairyTaleContentRepository;
import com.futurenet.sorisoopbackend.customfairytale.domain.CustomFairyTaleRepository;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.FindCustomFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.FindCustomFairyTaleDetailResponse;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.FindCustomFairyTaleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomFairyTaleServiceImpl implements CustomFairyTaleService {

    private final CustomFairyTaleRepository customFairyTaleRepository;
    private final CustomFairyTaleContentRepository customFairyTaleContentRepository;

    @Override
    public List<FindCustomFairyTaleResponse> getAllCustomFairyTaleByCategoryId(int categoryId, Long profileId) {
        return customFairyTaleRepository.getAllCustomFairyTaleByCategoryIdAndProfileId(categoryId, profileId);
    }

    @Override
    public List<FindCustomFairyTaleContentResponse> getCustomFairyTaleContents(Long customFairyTaleId, Long profileId) {
        List<FindCustomFairyTaleContentResponse> result = customFairyTaleContentRepository.getAllCustomFairyTaleContentsByCustomFairyTaleIdAndProfileId(customFairyTaleId, profileId);

        if (result.isEmpty()) {
            throw new CustomFairyTaleException(CustomFairyTaleErrorCode.FIND_CUSTOM_FAIRY_TALE_CONTENT_FAIL);
        }

        return result;
    }

    @Override
    public FindCustomFairyTaleDetailResponse getCustomFairyTaleDetail(Long customFairyTaleId, Long profileId) {
        FindCustomFairyTaleDetailResponse result = customFairyTaleRepository.getCustomFairyTaleDetailByCustomFairyTaleIdAndProfileId(customFairyTaleId, profileId);
        if (result == null) {
            throw new CustomFairyTaleException(CustomFairyTaleErrorCode.FIND_CUSTOM_FAIRY_TALE_DETAIL_FAIL);
        }
        return result;
    }
}
