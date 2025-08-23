package com.futurenet.sorisoopbackend.fairytale.application;

import com.futurenet.sorisoopbackend.fairytale.domain.FairyTaleRepository;

import com.futurenet.sorisoopbackend.fairytale.dto.response.FIndFairyTaleContentResponse;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FairyTaleCategoryResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}
