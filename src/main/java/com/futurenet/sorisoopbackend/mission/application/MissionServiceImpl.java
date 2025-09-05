package com.futurenet.sorisoopbackend.mission.application;

import com.futurenet.sorisoopbackend.mission.application.exception.MissionErrorCode;
import com.futurenet.sorisoopbackend.mission.application.exception.MissionException;
import com.futurenet.sorisoopbackend.mission.domain.MissionContentRepository;
import com.futurenet.sorisoopbackend.mission.domain.MissionRepository;
import com.futurenet.sorisoopbackend.mission.domain.MissionType;
import com.futurenet.sorisoopbackend.mission.dto.SaveMissionContentDto;
import com.futurenet.sorisoopbackend.mission.dto.SaveMissionDto;
import com.futurenet.sorisoopbackend.mission.dto.request.SaveMissionRequest;
import com.futurenet.sorisoopbackend.profile.domain.ProfileRepository;
import com.futurenet.sorisoopbackend.profile.domain.Role;
import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService {

    private final ProfileRepository profileRepository;
    private final MissionRepository missionRepository;
    private final MissionContentRepository missionContentRepository;

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
}