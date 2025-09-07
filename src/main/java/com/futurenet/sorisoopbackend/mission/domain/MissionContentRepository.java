package com.futurenet.sorisoopbackend.mission.domain;

import com.futurenet.sorisoopbackend.mission.dto.ReadCategoryMissionInfoDto;
import com.futurenet.sorisoopbackend.mission.dto.SaveMissionContentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MissionContentRepository {
    int saveMissionContent(@Param("request") SaveMissionContentDto request);
    int saveReadBookMission(@Param("request") SaveMissionContentDto request);
    List<Long> getTargetFairyTaleIdsByMissionId(Long missionId);
    ReadCategoryMissionInfoDto getReadCategoryMissionInfo(Long missionId);
    int getTargetCountByMissionId(Long missionId);
}
