package com.futurenet.sorisoopbackend.mission.dto;

import com.futurenet.sorisoopbackend.mission.domain.MissionStatus;
import com.futurenet.sorisoopbackend.mission.domain.MissionType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveMissionDto {
    private Long id;
    private String title;
    private Long childProfileId;
    private LocalDate startDate;
    private LocalDate endDate;
    private MissionType missionType;
    private Long parentProfileId;
    private MissionStatus missionStatus;
}
