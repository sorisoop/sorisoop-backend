package com.futurenet.sorisoopbackend.fairytale.domain;


import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleCategoryResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleContentResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FairyTaleRepository {
    List<FindFairyTaleContentResponse> getAllFairyTaleContentsByFairyTaleId(Long fairyTaleId);
    List<FairyTaleCategoryResponse> getAllFairyTaleCategories();
    List<FairyTaleResponse> getAllFairyTaleListByCategoryId(Long categoryId, int start, int size);
    List<FairyTaleResponse> getFairyTaleListByKeyword(String keyword, int start, int size);
    FairyTaleResponse getFairyTaleDetailByFairyTaleId(Long fairyTaleId);
}
