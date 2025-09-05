package com.futurenet.sorisoopbackend.mission.domain;

import com.futurenet.sorisoopbackend.mission.dto.SaveMissionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MissionRepository {
    int saveMission(@Param("request") SaveMissionDto request);
}
