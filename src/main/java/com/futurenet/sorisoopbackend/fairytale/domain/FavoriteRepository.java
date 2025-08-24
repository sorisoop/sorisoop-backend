package com.futurenet.sorisoopbackend.fairytale.domain;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavoriteRepository {
    int getFavoriteCount(Long fairyTaleId, Long profileId);
    int addFavorite(Long fairyTaleId, Long profileId);
}
