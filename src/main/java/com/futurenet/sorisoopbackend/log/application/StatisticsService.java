package com.futurenet.sorisoopbackend.log.application;

import com.futurenet.sorisoopbackend.log.dto.response.GetCategoryStatisticsResponse;
import com.futurenet.sorisoopbackend.log.dto.response.GetCompletionStatisticsResponse;

import java.util.List;

public interface StatisticsService {
    List<GetCategoryStatisticsResponse> getCategoryStatistics(Long childProfileId, Long parentProfileId, Long memberId);
    List<GetCompletionStatisticsResponse> getCompletionStatistics(Long childProfileId, Long parentProfileId, Long memberId);
}
