package com.futurenet.sorisoopbackend.mission.dto.request;

import com.futurenet.sorisoopbackend.mission.domain.MissionStatus;
import com.futurenet.sorisoopbackend.mission.domain.MissionType;
import com.futurenet.sorisoopbackend.mission.dto.SaveMissionContentDto;
import com.futurenet.sorisoopbackend.mission.dto.SaveMissionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveMissionRequest {
    private String title;
    private Long childProfileId;
    private LocalDate startDate;
    private LocalDate endDate;
    private MissionType missionType;
    private int targetCount;
    private List<Long> targetFairyTaleIds;
    private Long targetCategoryId;

    public SaveMissionDto toDto(Long parentProfileId) {
        return SaveMissionDto.builder()
                .title(title)
                .childProfileId(childProfileId)
                .startDate(startDate)
                .endDate(endDate)
                .missionType(missionType)
                .parentProfileId(parentProfileId)
                .missionStatus(calculateStatus())
                .build();
    }

    public SaveMissionContentDto toContentDto(Long missionId) {
        return SaveMissionContentDto.builder()
                .missionId(missionId)
                .targetCategoryId(targetCategoryId)
                .targetFairyTaleIds(targetFairyTaleIds)
                .targetCount(targetCount)
                .build();
    }

    private MissionStatus calculateStatus() {
        LocalDate today = LocalDate.now();
        if (!startDate.isAfter(today)) {
            return MissionStatus.ONGOING;
        } else {
            return MissionStatus.NOT_STARTED;
        }
    }
}
