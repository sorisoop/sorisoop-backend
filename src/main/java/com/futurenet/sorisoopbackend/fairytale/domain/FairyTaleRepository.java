package com.futurenet.sorisoopbackend.fairytale.domain;

import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleContentResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FairyTaleRepository {
    List<FindFairyTaleContentResponse> getAllFairyTaleContentsByFairyTaleId(Long fairyTaleId);
}
