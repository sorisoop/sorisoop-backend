package com.futurenet.sorisoopbackend.log.application;

import com.futurenet.sorisoopbackend.log.domain.ReadLogRepository;
import com.futurenet.sorisoopbackend.log.dto.request.SaveReadLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadLogServiceImpl implements ReadLogService {

    private final ReadLogRepository readLogRepository;

    @Override
    @Transactional
    public void saveReadLog(SaveReadLogRequest request, Long profileId) {
        readLogRepository.saveReadLog(request.toDto(profileId));
    }
}
