package com.futurenet.sorisoopbackend.mission.application;

import com.futurenet.sorisoopbackend.mission.dto.request.MissionCreateRequest;

public interface MissionService {
    int createMission(MissionCreateRequest request);
}
