package com.futurenet.sorisoopbackend.mission.application;

import com.futurenet.sorisoopbackend.mission.application.exception.MissionErrorCode;
import com.futurenet.sorisoopbackend.mission.application.exception.MissionException;
import com.futurenet.sorisoopbackend.mission.domain.MissionRepository;
import com.futurenet.sorisoopbackend.mission.dto.request.MissionCreateRequest;
import com.futurenet.sorisoopbackend.profile.domain.ProfileRepository;
import com.futurenet.sorisoopbackend.profile.domain.Role;
import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService {
    private final MissionRepository missionRepository;

    @Override
    public int createMission(MissionCreateRequest request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new MissionException(MissionErrorCode.INVALID_DATE_RANGE);
        }

        int result = 0;
        switch (request.getMissionType()) {
            case CREATE_FAIRY_TALE:
                result = missionRepository.insertCreateFairyTaleMission(request);
                break;

            case READ_CATEGORY:
                if (request.getCategoryId() == null) {
                    throw new MissionException(MissionErrorCode.CATEGORY_NOT_FOUND);
                }

                if (request.getBookCount() == null || request.getBookCount() <= 0) {
                    throw new MissionException(MissionErrorCode.BOOK_COUNT_INVALID);
                }

                result = missionRepository.insertReadCategoryMission(request);
                break;

            case READ_BOOK:
                if (request.getFairyTaleId() == null) {
                    throw new MissionException(MissionErrorCode.FAIRY_TALE_NOT_FOUND);
                }

                result = missionRepository.insertReadFairyTaleMission(request);
                break;
            default:
                throw new MissionException(MissionErrorCode.UNKNOWN_MISSION_TYPE);
        }

        if (result <= 0) {
            throw new MissionException(MissionErrorCode.DB_INSERT_FAILED);
        }
        return result;
    }


}


