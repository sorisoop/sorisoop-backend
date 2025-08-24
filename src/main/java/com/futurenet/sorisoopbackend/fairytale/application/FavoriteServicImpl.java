package com.futurenet.sorisoopbackend.fairytale.application;

import com.futurenet.sorisoopbackend.fairytale.domain.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteServicImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Override
    @Transactional
    public void addFavorite(Long fairyTaleId, Long profileId) {
        int cnt = favoriteRepository.getFavoriteCount(fairyTaleId, profileId);
        if(cnt > 0){
            return;
        }
        favoriteRepository.addFavorite(fairyTaleId, profileId);
    }
}
