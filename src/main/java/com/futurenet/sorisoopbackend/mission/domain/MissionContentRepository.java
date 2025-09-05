package com.futurenet.sorisoopbackend.mission.domain;

import com.futurenet.sorisoopbackend.mission.dto.SaveMissionContentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MissionContentRepository {
    int saveMissionContent(@Param("request") SaveMissionContentDto request);
    int saveReadBookMission(@Param("request") SaveMissionContentDto request);
}
