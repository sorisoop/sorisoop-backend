package com.futurenet.sorisoopbackend.mission.application;

import com.futurenet.sorisoopbackend.mission.dto.request.SaveMissionRequest;
import com.futurenet.sorisoopbackend.mission.dto.response.GetAssignedMissionResponse;
import com.futurenet.sorisoopbackend.mission.dto.response.GetGivenMissionResponse;
import com.futurenet.sorisoopbackend.mission.dto.response.GetMissionDetailResponse;

import java.util.List;

public interface MissionService {
    void saveMission(SaveMissionRequest request, Long parentProfileId, Long memberId);
    List<GetGivenMissionResponse> getAllGivenMission(Long childProfileId, Long profileId);
    List<GetAssignedMissionResponse> getAllAssignedMission(Long profileId);
    GetMissionDetailResponse getMissionDetail(Long missionId);
    void deleteMission(Long missionId, Long profileId);
}
