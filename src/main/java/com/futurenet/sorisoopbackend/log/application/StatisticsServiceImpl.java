package com.futurenet.sorisoopbackend.log.application;

import com.futurenet.sorisoopbackend.fairytale.domain.FairyTaleRepository;
import com.futurenet.sorisoopbackend.fairytale.dto.response.FindFairyTaleCategoryResponse;
import com.futurenet.sorisoopbackend.log.domain.ReadLogRepository;
import com.futurenet.sorisoopbackend.log.dto.GetCategoryStatisticsDto;
import com.futurenet.sorisoopbackend.log.dto.response.GetCategoryStatisticsResponse;
import com.futurenet.sorisoopbackend.log.dto.response.GetCompletionStatisticsResponse;
import com.futurenet.sorisoopbackend.mission.application.exception.MissionErrorCode;
import com.futurenet.sorisoopbackend.mission.application.exception.MissionException;
import com.futurenet.sorisoopbackend.profile.domain.ProfileRepository;
import com.futurenet.sorisoopbackend.profile.domain.Role;
import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final ReadLogRepository readLogRepository;
    private final FairyTaleRepository fairyTaleRepository;
    private final ReadLogUtil readLogUtil;
    private final ProfileRepository profileRepository;

    @Override
    public List<GetCategoryStatisticsResponse> getCategoryStatistics(Long childProfileId, Long parentProfileId, Long memberId) {
        checkIsParents(childProfileId, parentProfileId, memberId);

        List<GetCategoryStatisticsResponse> result = new ArrayList<>();

        for (int duration : List.of(30, 90)) {
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(duration);

            List<FindFairyTaleCategoryResponse> categories = fairyTaleRepository.getAllFairyTaleCategories();
            List<GetCategoryStatisticsDto> stats = new ArrayList<>();

            for (FindFairyTaleCategoryResponse category : categories) {
                Long categoryId = category.getId();
                String categoryName = category.getName();

                List<Long> bookIds = readLogRepository.getReadBookIdsByCategoryAndDate(childProfileId, categoryId, startDate, endDate);

                int fullyReadCount = 0;
                for (Long bookId : bookIds) {
                    int pageCount = fairyTaleRepository.getPageCountByBookId(bookId);
                    if (readLogUtil.isFullyRead(childProfileId, bookId, pageCount, startDate, endDate)) {
                        fullyReadCount++;
                    }
                }

                stats.add(new GetCategoryStatisticsDto(categoryId.intValue(), categoryName, fullyReadCount));
            }

            result.add(new GetCategoryStatisticsResponse(duration, stats));
        }

        return result;
    }

    @Override
    public List<GetCompletionStatisticsResponse> getCompletionStatistics(Long childProfileId, Long parentProfileId, Long memberId) {
        checkIsParents(childProfileId, parentProfileId, memberId);

        List<GetCompletionStatisticsResponse> result = new ArrayList<>();
        for (int duration : List.of(30, 90)) {
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(duration);

            List<Long> readBookIds = readLogRepository.getAllReadBookIds(childProfileId, startDate, endDate);

            int completeReadCount = 0;
            for (Long bookId : readBookIds) {
                int pageCount = fairyTaleRepository.getPageCountByBookId(bookId);
                if (readLogUtil.isFullyRead(childProfileId, bookId, pageCount, startDate, endDate)) {
                    completeReadCount++;
                }
            }
            result.add(new GetCompletionStatisticsResponse(duration, readBookIds.size(), completeReadCount));
        }

        return result;
    }

    private void checkIsParents(Long childProfileId, Long parentProfileId, Long memberId) {
        FindProfileResponse parentProfile = profileRepository.getProfileByProfileId(parentProfileId);

        if (parentProfile == null || parentProfile.getRole() != Role.PARENT) {
            throw new MissionException(MissionErrorCode.NOT_PARENT_PROFILE);
        }

        Long childMemberId = profileRepository.findMemberIdByProfileId(childProfileId);

        if (childMemberId == null || !childMemberId.equals(memberId)) {
            throw new MissionException(MissionErrorCode.NOT_PARENT_PROFILE);
        }
    }
}
