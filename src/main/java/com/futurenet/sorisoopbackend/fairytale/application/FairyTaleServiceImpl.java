package com.futurenet.sorisoopbackend.fairytale.application;

import com.futurenet.sorisoopbackend.fairytale.domain.FairyTaleRepository;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleCategoryResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleContentResponse;
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
    public List<FairyTaleCategoryResponse> getFairyTaleCategories() {
        return fairyTaleRepository.getAllFairyTaleCategories();
    }

    @Override
    @Transactional
    public List<FairyTaleResponse> getFairyTaleList(Long categoryId, int page) {
        int start = (page - 1) * PAGE_SIZE;
        return fairyTaleRepository.getAllFairyTaleListByCategoryId(categoryId, start, PAGE_SIZE);
    }

}
