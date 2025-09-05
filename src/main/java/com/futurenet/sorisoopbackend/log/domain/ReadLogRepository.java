package com.futurenet.sorisoopbackend.log.domain;

import com.futurenet.sorisoopbackend.log.dto.SaveReadLogDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReadLogRepository {
    void saveReadLog(@Param("request") SaveReadLogDto request);
}
