package com.futurenet.sorisoopbackend.fairytale.domain;


import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleCategoryResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleContentResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FairyTaleRepository {
    List<FindFairyTaleContentResponse> getAllFairyTaleContentsByFairyTaleId(Long fairyTaleId);
    List<FindFairyTaleCategoryResponse> getAllFairyTaleCategories();
    List<FindFairyTaleResponse> getAllFairyTaleListByCategoryId(Long categoryId, int start, int size, Long profileId);
    List<FindFairyTaleResponse> getFairyTaleListByKeyword(String keyword, int start, int size, Long profileId) ;
    FindFairyTaleResponse getFairyTaleDetailByFairyTaleId(Long fairyTaleId, Long profileId);
}
