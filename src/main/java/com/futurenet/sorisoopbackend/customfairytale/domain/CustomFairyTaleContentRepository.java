package com.futurenet.sorisoopbackend.customfairytale.domain;

import com.futurenet.sorisoopbackend.customfairytale.dto.request.SaveCustomFairyTaleContentRequest;
import com.futurenet.sorisoopbackend.customfairytale.dto.response.FindCustomFairyTaleContentResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomFairyTaleContentRepository {
    int saveCustomFairyTaleContents(@Param("contents") List<SaveCustomFairyTaleContentRequest> contents);
    List<FindCustomFairyTaleContentResponse> getAllCustomFairyTaleContentsByCustomFairyTaleIdAndProfileId(Long customFairyTaleId, Long profileId);
}
