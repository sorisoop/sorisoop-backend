package com.futurenet.sorisoopbackend.mission.dto;

import com.futurenet.sorisoopbackend.mission.domain.MissionType;

import java.time.LocalDate;

public interface GetMissionResponse {
    Long getMissionId();
    MissionType getMissionType();
    LocalDate getStartDate();
    LocalDate getEndDate();
}
