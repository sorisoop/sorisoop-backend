package com.futurenet.sorisoopbackend.fairytale.application;

import com.futurenet.sorisoopbackend.fairytale.domain.FairyTaleRepository;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleCategoryResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.futurenet.sorisoopbackend.global.constant.PaginationConstant.PAGE_SIZE;


@Service
@RequiredArgsConstructor
public class FairyTaleServiceImpl implements FairyTaleService {

    private final FairyTaleRepository fairyTaleRepository;

    @Override
    public List<FindFairyTaleContentResponse> getFairyTaleContents(Long fairyTaleId) {
        return fairyTaleRepository.getAllFairyTaleContentsByFairyTaleId(fairyTaleId);
    }

    @Override
    @Transactional
    public List<FindFairyTaleCategoryResponse> getFairyTaleCategories() {
        return fairyTaleRepository.getAllFairyTaleCategories();
    }

    @Override
    @Transactional
    public List<FindFairyTaleResponse> getFairyTaleList(Long categoryId, int page, Long profileId) {
        int start = (page - 1) * PAGE_SIZE;
        return fairyTaleRepository.getAllFairyTaleListByCategoryId(categoryId, start, PAGE_SIZE, profileId);
    }

    @Override
    @Transactional
    public List<FindFairyTaleResponse> searchFairyTaleList(String keyword, int page, Long profileId) {
        int start = (page - 1) * PAGE_SIZE;
        return fairyTaleRepository.getFairyTaleListByKeyword(keyword, start, PAGE_SIZE, profileId);
    }

    @Override
    @Transactional
    public FindFairyTaleResponse getFairyTaleDetail(Long fairyTaleId, Long profileId) {
        return fairyTaleRepository.getFairyTaleDetailByFairyTaleId(fairyTaleId, profileId);
    }

    @Override
    public List<FindFairyTaleResponse> getFairyTalesRandom(Long profileId) {
        return fairyTaleRepository.getFairyTalesRandom(profileId);
    }
}
