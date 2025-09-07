package com.futurenet.sorisoopbackend.mission.dto;

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
public class GetMissionDto {
    private Long missionId;
    private MissionType missionType;
    private Long childProfileId;
    private LocalDate startDate;
    private LocalDate endDate;
}
