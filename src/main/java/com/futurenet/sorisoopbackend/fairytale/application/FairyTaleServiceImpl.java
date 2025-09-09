package com.futurenet.sorisoopbackend.fairytale.application;

import com.futurenet.sorisoopbackend.fairytale.application.exception.FairyTaleErrorCode;
import com.futurenet.sorisoopbackend.fairytale.application.exception.FairyTaleException;
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
        List<FindFairyTaleContentResponse> result = fairyTaleRepository.getAllFairyTaleContentsByFairyTaleId(fairyTaleId);

        if(result == null || result.isEmpty()) {
            throw new FairyTaleException(FairyTaleErrorCode.FIND_FAIRY_TALE_CONTENT_FAIL);
        }
        return result;
    }

    @Override
    @Transactional
    public List<FindFairyTaleCategoryResponse> getFairyTaleCategories() {
        List<FindFairyTaleCategoryResponse> result = fairyTaleRepository.getAllFairyTaleCategories();

        if(result == null || result.isEmpty()) {
            throw new FairyTaleException(FairyTaleErrorCode.FIND_FAIRY_TALE_CATEGORY_FAIL);
        }
        return result;
    }

    @Override
    @Transactional
    public List<FindFairyTaleResponse> getFairyTaleList(Long categoryId, int page, Long profileId) {
        if(page <= 0){
            throw new FairyTaleException(FairyTaleErrorCode.INVALID_PAGE_REQUEST);
        }

        int start = (page - 1) * PAGE_SIZE;
        List<FindFairyTaleResponse> result = fairyTaleRepository.getAllFairyTaleListByCategoryId(categoryId, start, PAGE_SIZE, profileId);

        if (result == null || result.isEmpty()) {
            throw new FairyTaleException(FairyTaleErrorCode.FIND_FAIRY_TALE_LIST_FAIL);
        }
        return result;
    }

    @Override
    @Transactional
    public List<FindFairyTaleResponse> searchFairyTaleList(String keyword, int page, Long profileId) {
        if(page <= 0){
            throw new FairyTaleException(FairyTaleErrorCode.INVALID_PAGE_REQUEST);
        }
        int start = (page - 1) * PAGE_SIZE;

        return fairyTaleRepository.getFairyTaleListByKeyword(keyword, start, PAGE_SIZE, profileId);
    }

    @Override
    @Transactional
    public FindFairyTaleResponse getFairyTaleDetail(Long fairyTaleId, Long profileId) {
        FindFairyTaleResponse result = fairyTaleRepository.getFairyTaleDetailByFairyTaleId(fairyTaleId, profileId);

        if(result == null) {
            throw new FairyTaleException(FairyTaleErrorCode.FIND_FAIRY_TALE_DETAIL_FAIL);
        }
        return result;
    }

    @Override
    public List<FindFairyTaleResponse> getFairyTalesRandom(Long profileId) {
        List<FindFairyTaleResponse> result = fairyTaleRepository.getFairyTalesRandom(profileId);

        if(result == null || result.isEmpty()) {
            throw new FairyTaleException(FairyTaleErrorCode.FIND_FAIRY_TALE_LIST_FAIL);
        }
        return result;
    }
}
