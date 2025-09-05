package com.futurenet.sorisoopbackend.mission.application;

import com.futurenet.sorisoopbackend.mission.dto.request.SaveMissionRequest;

public interface MissionService {
    void saveMission(SaveMissionRequest request, Long parentProfileId, Long memberId);
}
