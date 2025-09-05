package com.futurenet.sorisoopbackend.log.application;

import com.futurenet.sorisoopbackend.log.dto.request.SaveReadLogRequest;

public interface ReadLogService {
    void saveReadLog(SaveReadLogRequest request, Long profileId);
}
