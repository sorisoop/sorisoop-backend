package com.futurenet.sorisoopbackend.fairytale.application;

import com.futurenet.sorisoopbackend.fairytale.domain.FavoriteRepository;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.futurenet.sorisoopbackend.global.constant.PaginationConstant.PAGE_SIZE;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Override
    @Transactional
    public void addFavorite(Long fairyTaleId, Long profileId) {
        boolean isExist = favoriteRepository.getFavoriteCount(fairyTaleId, profileId);
        if(isExist){
            return;
        }
        favoriteRepository.addFavorite(fairyTaleId, profileId);
    }

    @Override
    @Transactional
    public List<FairyTaleResponse> getFavoriteFairyTales(Long profileId, int page) {
        int start = (page -1) * PAGE_SIZE;
        return favoriteRepository.getFavoriteFairyTalesByProfileId(profileId, start, PAGE_SIZE);
    }

    @Override
    @Transactional
    public void deleteFavorite(Long fairyTaleId, Long profileId) {
        boolean isExist = favoriteRepository.getFavoriteCount(fairyTaleId, profileId);
        if(!isExist){
            return;
        }
        favoriteRepository.deleteFavorite(fairyTaleId, profileId);
    }
}
