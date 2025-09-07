package com.futurenet.sorisoopbackend.mission.dto.response;

import com.futurenet.sorisoopbackend.mission.domain.MissionStatus;
import com.futurenet.sorisoopbackend.mission.domain.MissionType;
import com.futurenet.sorisoopbackend.mission.dto.GetMissionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetGivenMissionResponse implements GetMissionResponse {
    private Long missionId;
    private String title;
    private String childName; // 숙제 대상 이름
    private MissionType missionType;
    private LocalDate startDate;
    private LocalDate endDate;
    private MissionStatus missionStatus;
    private int progressRate; // 달성률
}
