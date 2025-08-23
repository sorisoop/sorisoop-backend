package com.futurenet.sorisoopbackend.fairytale.domain;


import com.futurenet.sorisoopbackend.fairytale.dto.response.FIndFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleCategoryResponse;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FairyTaleRepository {

    List<FIndFairyTaleContentResponse> getAllFairyTaleContentsByFairyTaleId(Long fairyTaleId);
    List<FairyTaleCategoryResponse> getAllFairyTaleCategories();

}
