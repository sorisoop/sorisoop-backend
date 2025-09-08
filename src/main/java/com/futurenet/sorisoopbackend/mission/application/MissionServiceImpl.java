package com.futurenet.sorisoopbackend.mission.application;

import com.futurenet.sorisoopbackend.fairytale.domain.FairyTaleRepository;
import com.futurenet.sorisoopbackend.fairytale.dto.GetFairyTaleInfoDto;
import com.futurenet.sorisoopbackend.log.application.ReadLogUtil;
import com.futurenet.sorisoopbackend.log.domain.ReadLogRepository;
import com.futurenet.sorisoopbackend.mission.application.exception.MissionErrorCode;
import com.futurenet.sorisoopbackend.mission.application.exception.MissionException;
import com.futurenet.sorisoopbackend.mission.domain.MissionContentRepository;
import com.futurenet.sorisoopbackend.mission.domain.MissionRepository;
import com.futurenet.sorisoopbackend.mission.domain.MissionStatus;
import com.futurenet.sorisoopbackend.mission.domain.MissionType;
import com.futurenet.sorisoopbackend.mission.dto.*;
import com.futurenet.sorisoopbackend.mission.dto.request.SaveMissionRequest;
import com.futurenet.sorisoopbackend.mission.dto.response.GetAssignedMissionResponse;
import com.futurenet.sorisoopbackend.mission.dto.response.GetGivenMissionResponse;
import com.futurenet.sorisoopbackend.mission.dto.response.GetMissionDetailResponse;
import com.futurenet.sorisoopbackend.profile.domain.ProfileRepository;
import com.futurenet.sorisoopbackend.profile.domain.Role;
import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService {

    private final ProfileRepository profileRepository;
    private final MissionRepository missionRepository;
    private final MissionContentRepository missionContentRepository;
    private final FairyTaleRepository fairyTaleRepository;
    private final ReadLogRepository readLogRepository;
    private final ReadLogUtil readLogUtil;

    @Override
    @Transactional
    public void saveMission(SaveMissionRequest request, Long parentProfileId, Long memberId) {

        if (request.getStartDate().isBefore(LocalDate.now())) {
            throw new MissionException(MissionErrorCode.INVALID_START_DATE);
        }

        // parentProfileId가 부모 타입인지 확인하고 자식과 같은 memberId인지 확인
        FindProfileResponse parentProfile = profileRepository.getProfileByProfileId(parentProfileId);

        if (parentProfile == null || parentProfile.getRole() != Role.PARENT) {
            throw new MissionException(MissionErrorCode.NOT_PARENT_PROFILE);
        }

        Long childMemberId = profileRepository.findMemberIdByProfileId(request.getChildProfileId());
        if (childMemberId == null || !childMemberId.equals(memberId)) {
            throw new MissionException(MissionErrorCode.NOT_PARENT_PROFILE);
        }

        SaveMissionDto saveMissionDto = request.toDto(parentProfileId);
        int saveMissionResult = missionRepository.saveMission(saveMissionDto);

        if (saveMissionResult == 0) {
            throw new MissionException(MissionErrorCode.SAVE_MISSION_FAIL);
        }

        saveMissionContent(request.toContentDto(saveMissionDto.getId()), request.getMissionType());
    }

    @Transactional
    public void saveMissionContent(SaveMissionContentDto request, MissionType missionType) {
        if (missionType == MissionType.READ_BOOK) {
            int result = missionContentRepository.saveReadBookMission(request);

            if (result == 0) {
                throw new MissionException(MissionErrorCode.SAVE_MISSION_FAIL);
            }
        } else {
            int result = missionContentRepository.saveMissionContent(request);

            if (result == 0) {
                throw new MissionException(MissionErrorCode.SAVE_MISSION_FAIL);
            }
        }
    }

    @Override
    @Transactional
    public List<GetGivenMissionResponse> getAllGivenMission(Long childProfileId, Long profileId) {
        FindProfileResponse profile = profileRepository.getProfileByProfileId(profileId);
        if (profile.getRole() != Role.PARENT) {
            throw new MissionException(MissionErrorCode.NOT_PARENT_PROFILE);
        }

        List<GetGivenMissionResponse> missions =  missionRepository.getAllGivenMission(childProfileId, profileId);
        for (GetGivenMissionResponse mission : missions) {
            if (mission.getMissionStatus() == MissionStatus.COMPLETED) {
                mission.setProgressRate(100);
            } else {
                int progressRate = calculateProgressRate(mission, childProfileId);
                mission.setProgressRate(progressRate);
                updateMissionStateIfCompleted(mission, progressRate);
            }
        }

        return missions;
    }

    @Override
    @Transactional
    public List<GetAssignedMissionResponse> getAllAssignedMission(Long profileId) {
        List<GetAssignedMissionResponse> missions = missionRepository.getAllAssignedMission(profileId);
        for (GetAssignedMissionResponse mission : missions) {
            if (mission.getMissionStatus() == MissionStatus.COMPLETED) {
                mission.setProgressRate(100);
            } else {
                int progressRate = calculateProgressRate(mission, profileId);
                mission.setProgressRate(progressRate);
                updateMissionStateIfCompleted(mission, progressRate);
            }
        }
        return missions;
    }

    @Override
    public GetMissionDetailResponse getMissionDetail(Long missionId) {
        GetMissionDto mission = missionRepository.getMissionByMissionId(missionId);

        if (mission == null) {
            throw new MissionException(MissionErrorCode.FIND_MISSION_FAIL);
        }

        return switch (mission.getMissionType()) {
            case READ_BOOK -> getReadBookMissionDetail(mission);
            case READ_CATEGORY -> getReadCategoryMissionDetail(mission);
            case CREATE_FAIRY_TALE -> getCreateFairyTaleMissionDetail(mission);
        };
    }

    @Override
    @Transactional
    public void deleteMission(Long missionId, Long profileId) {
        int result = missionRepository.deleteMissionByMissionIdAndProfileId(missionId, profileId);

        if (result == 0) {
            throw new MissionException(MissionErrorCode.DELETE_MISSION_FAIL);
        }
    }

    @Override
    @Transactional
    public void updateMissionStatusOngoing() {
        LocalDate today = LocalDate.now();
        missionRepository.updateMissionStatusOngoing(today);
    }

    @Override
    @Transactional
    public void updateMissionStatusFailed() {
        LocalDate today = LocalDate.now();

        List<GetMissionDto> candidates = missionRepository.findNotCompletedMissionBeforeEndDate(today);

        for (GetMissionDto mission : candidates) {
            int progressRate = calculateProgressRate(mission, mission.getChildProfileId());

            if (progressRate < 100) {
                missionRepository.updateMissionStatus(mission.getMissionId(), MissionStatus.FAILED);
            } else {
                missionRepository.updateMissionStatus(mission.getMissionId(), MissionStatus.COMPLETED);
            }
        }
    }

    private void updateMissionStateIfCompleted(GetMissionResponse mission, int progressRate) {
        if (progressRate == 100 && mission.getMissionStatus() == MissionStatus.ONGOING) {
            missionRepository.updateMissionStatus(mission.getMissionId(), MissionStatus.COMPLETED);
            mission.setMissionStatus(MissionStatus.COMPLETED);
        }
    }

    /**
     * 미션 달성률 계산 타입별 분기처리
     * */
    public int calculateProgressRate(GetMissionResponse mission, Long childProfileId) {
        return switch (mission.getMissionType()) {
            case MissionType.READ_BOOK -> calculateReadBookProgress(mission, childProfileId);
            case MissionType.READ_CATEGORY -> calculateReadCategoryProgress(mission, childProfileId);
            case MissionType.CREATE_FAIRY_TALE -> calculateCreateFairyTaleProgress(mission, childProfileId);
        };
    }

    /**
     * 특정 책 읽기 미션 달성률 계산
     * */
    public int calculateReadBookProgress(GetMissionResponse mission, Long childProfileId) {
        Long missionId = mission.getMissionId();
        LocalDate startDate = mission.getStartDate();
        LocalDate endDate = mission.getEndDate();

        List<Long> bookIds = missionContentRepository.getTargetFairyTaleIdsByMissionId(missionId);

        if (bookIds.isEmpty()) {
            throw new MissionException(MissionErrorCode.FIND_MISSION_FAIL);
        }

        int successCount = 0;
        for (Long bookId : bookIds) {
            int pageCount = fairyTaleRepository.getPageCountByBookId(bookId);
            boolean isComplete = readLogUtil.isFullyRead(childProfileId, bookId, pageCount, startDate, endDate);

            if (isComplete) successCount++;
        }

        return (int) ((successCount / (double) bookIds.size()) * 100);
    }

    /**
     * 특정 카테고리 읽기 미션 달성률 계산
     * */
    public int calculateReadCategoryProgress(GetMissionResponse mission, Long childProfileId) {
        ReadCategoryMissionInfoDto readCategoryMissionInfo = missionContentRepository.getReadCategoryMissionInfo(mission.getMissionId());
        int targetCount = readCategoryMissionInfo.getTargetCount();
        Long categoryId = readCategoryMissionInfo.getTargetCategoryId();

        List<Long> candidateBookIds = readLogRepository.getReadBookIdsByCategoryAndDate(childProfileId, categoryId, mission.getStartDate(), mission.getEndDate());

        int fullyReadCount = 0;
        for (Long bookId : candidateBookIds) {
            int pageCount = fairyTaleRepository.getPageCountByBookId(bookId);
            boolean isFullyRead = readLogUtil.isFullyRead(childProfileId, bookId, pageCount, mission.getStartDate(), mission.getEndDate());

            if (isFullyRead) fullyReadCount++;
        }
        int progressRate = (int) (((double) fullyReadCount / targetCount) * 100);
        return Math.min(progressRate, 100);
    }


    /**
     * 첵 셍성 미션 달성률 계산
     * */
    public int calculateCreateFairyTaleProgress(GetMissionResponse mission, Long childProfileId) {
        Long missionId = mission.getMissionId();
        LocalDate startDate = mission.getStartDate();
        LocalDate endDate = mission.getEndDate();

        int targetCount = missionContentRepository.getTargetCountByMissionId(missionId);
        int createdCount = readLogRepository.countCreatedFairyTales(childProfileId, startDate, endDate);

        int progressRate = (int) (((double) createdCount / targetCount) * 100);
        return Math.min(progressRate, 100);
    }


    /**
     * 특정 책 읽기 미션 상세조회
     * */
    private GetMissionDetailResponse getReadBookMissionDetail(GetMissionDto mission) {
        List<Long> bookIds = missionContentRepository.getTargetFairyTaleIdsByMissionId(mission.getMissionId());

        List<ReadBookMissionInfoDto> readBookMissionInfoDtos = bookIds.stream().map(bookId -> {
            GetFairyTaleInfoDto fairyTaleInfo = fairyTaleRepository.getFairyTaleInfo(bookId);
            boolean isRead = false;
            if (mission.getMissionStatus() == MissionStatus.COMPLETED) {
                isRead = true;
            } else if (mission.getMissionStatus() == MissionStatus.ONGOING) {
                isRead = readLogUtil.isFullyRead(mission.getChildProfileId(), bookId, fairyTaleInfo.getPageCount(), mission.getStartDate(), mission.getEndDate());
            }

            return new ReadBookMissionInfoDto(bookId, fairyTaleInfo.getTitle(), fairyTaleInfo.getThumbnailImage(), isRead);
        }).toList();

        return new GetMissionDetailResponse(mission.getMissionType(), 0, 0, null, readBookMissionInfoDtos);
    }

    /**
     * 특정 카테고리 읽기 미션 상세조회
     * */
    private GetMissionDetailResponse getReadCategoryMissionDetail(GetMissionDto mission) {
        ReadCategoryMissionInfoDto content = missionContentRepository.getReadCategoryMissionInfo(mission.getMissionId());

        int targetCount = content.getTargetCount();
        Long categoryId = content.getTargetCategoryId();
        String categoryName = fairyTaleRepository.getFairyTaleCategoryName(categoryId);

        if (mission.getMissionStatus() == MissionStatus.COMPLETED) {
            return new GetMissionDetailResponse(mission.getMissionType(), targetCount, targetCount, categoryName, null);
        }

        if (mission.getMissionStatus() == MissionStatus.NOT_STARTED) {
            return new GetMissionDetailResponse(mission.getMissionType(), targetCount, 0, categoryName, null);
        }

        List<Long> candidateBookIds = readLogRepository.getReadBookIdsByCategoryAndDate(mission.getChildProfileId(), categoryId, mission.getStartDate(), mission.getEndDate());

        int completedCount = 0;
        for (Long bookId : candidateBookIds) {
            int pageCount = fairyTaleRepository.getPageCountByBookId(bookId);
            if (readLogUtil.isFullyRead(mission.getChildProfileId(), bookId, pageCount, mission.getStartDate(), mission.getEndDate())) {
                completedCount++;
            }
        }

        return new GetMissionDetailResponse(mission.getMissionType(), targetCount, completedCount, categoryName, null);
    }

    /**
     * 책 생성 미션 상세조회
     * */
    private GetMissionDetailResponse getCreateFairyTaleMissionDetail(GetMissionDto mission) {
        int targetCount = missionContentRepository.getTargetCountByMissionId(mission.getMissionId());

        if (mission.getMissionStatus() == MissionStatus.COMPLETED) {
            return new GetMissionDetailResponse(mission.getMissionType(), targetCount, targetCount, null, null);
        }

        if (mission.getMissionStatus() == MissionStatus.NOT_STARTED) {
            return new GetMissionDetailResponse(mission.getMissionType(), targetCount, 0, null, null);
        }

        int completedCount = readLogRepository.countCreatedFairyTales(mission.getChildProfileId(), mission.getStartDate(), mission.getEndDate());

        return new GetMissionDetailResponse(mission.getMissionType(), targetCount, completedCount, null, null);
    }

}