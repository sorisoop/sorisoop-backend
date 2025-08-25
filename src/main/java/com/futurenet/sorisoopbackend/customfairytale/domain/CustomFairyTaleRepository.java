package com.futurenet.sorisoopbackend.customfairytale.domain;

import com.futurenet.sorisoopbackend.customfairytale.dto.request.SaveCustomFairyTaleRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.FindCustomFairyTaleDetailResponse;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.FindCustomFairyTaleResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomFairyTaleRepository {
    int saveCustomFairyTale(@Param("request") SaveCustomFairyTaleRequest request);
    List<FindCustomFairyTaleResponse> getAllCustomFairyTaleByCategoryIdAndProfileId(@Param("categoryId") int categoryId, @Param("profileId") Long profileId);
    FindCustomFairyTaleDetailResponse getCustomFairyTaleDetailByCustomFairyTaleIdAndProfileId(Long customFairyTaleId, Long profileId);
}
