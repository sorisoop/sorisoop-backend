package com.futurenet.sorisoopbackend.mission.dto.response;

import com.futurenet.sorisoopbackend.mission.domain.MissionType;
import com.futurenet.sorisoopbackend.mission.dto.ReadBookMissionInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetMissionDetailResponse {
    private MissionType missionType;
    private int targetCount; // 카테고리 미션이나 그리기 미션의 경우 사용
    private int completedCount; // 카테고리 미션이나 그리기 미션의 경우 사용
    private String category; // 카테고리 미션의 경우 사용
    private List<ReadBookMissionInfoDto> readBookMissionInfoDtos; // 특정 책 읽기 미션의 경우
}
