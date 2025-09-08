package com.futurenet.sorisoopbackend.log.application;

import com.futurenet.sorisoopbackend.log.domain.ReadLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ReadLogUtil {

    private final ReadLogRepository readLogRepository;

    public boolean isFullyRead(Long profileId, Long bookId, int pageCount, LocalDate startDate, LocalDate endDate) {
        List<Integer> readPages = readLogRepository.getReadPages(profileId, bookId, pageCount, startDate, endDate);

        Set<Integer> readPageSet = new HashSet<>(readPages);
        for (int page = 1; page <= pageCount; page++) {
            if (!readPageSet.contains(page)) return false;
        }

        return true;
    }
}
