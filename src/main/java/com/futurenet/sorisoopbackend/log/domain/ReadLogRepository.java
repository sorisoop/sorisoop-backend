package com.futurenet.sorisoopbackend.log.domain;

import com.futurenet.sorisoopbackend.log.dto.SaveReadLogDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReadLogRepository {
    void saveReadLog(@Param("request") SaveReadLogDto request);
    List<Integer> getReadPages(Long profileId, Long bookId, int pageCount, LocalDate startDate, LocalDate endDate);
    List<Long> getReadBookIdsByCategoryAndDate(Long profileId, Long categoryId, LocalDate startDate, LocalDate endDate);
    int countCreatedFairyTales(Long profileId, LocalDate startDate, LocalDate endDate);
    List<Long> getAllReadBookIds(Long profileId, LocalDate startDate, LocalDate endDate);
}
