package com.futurenet.sorisoopbackend.mission.domain;

import com.futurenet.sorisoopbackend.mission.dto.request.MissionCreateRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MissionRepository {
    int insertCreateFairyTaleMission(@Param("request") MissionCreateRequest request);
    int insertReadCategoryMission(@Param("request") MissionCreateRequest request);
    int insertReadFairyTaleMission(@Param("request") MissionCreateRequest request);
}
