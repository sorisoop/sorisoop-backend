package com.futurenet.sorisoopbackend.fairytale.domain;

import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteRepository {
    boolean getFavoriteCount(Long fairyTaleId, Long profileId);
    int addFavorite(Long fairyTaleId, Long profileId);
    List<FairyTaleResponse> getFavoriteFairyTalesByProfileId(Long profileId, int start, int size);
    int deleteFavorite(Long fairyTaleId, Long profileId);
}
