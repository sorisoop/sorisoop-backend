package com.futurenet.sorisoopbackend.fairytale.application;

import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleResponse;

import java.util.List;

public interface FavoriteService {
    void addFavorite(Long fairyTaleId, Long profileId);
    List<FindFairyTaleResponse> getFavoriteFairyTales(Long profileId, int page);
    void deleteFavorite(Long fairyTaleId, Long profileId);
}
