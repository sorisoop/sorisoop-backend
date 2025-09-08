package com.futurenet.sorisoopbackend.mission.dto;

import com.futurenet.sorisoopbackend.mission.domain.MissionStatus;
import com.futurenet.sorisoopbackend.mission.domain.MissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetMissionDto implements GetMissionResponse {
    private Long missionId;
    private MissionType missionType;
    private MissionStatus missionStatus;
    private Long childProfileId;
    private LocalDate startDate;
    private LocalDate endDate;
}
