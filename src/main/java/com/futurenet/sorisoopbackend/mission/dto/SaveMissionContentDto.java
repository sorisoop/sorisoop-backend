package com.futurenet.sorisoopbackend.mission.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveMissionContentDto {
    private Long missionId;
    private int targetCount;
    private List<Long> targetFairyTaleIds;
    private Long targetCategoryId;
}
