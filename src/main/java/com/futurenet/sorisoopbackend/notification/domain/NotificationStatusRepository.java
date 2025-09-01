package com.futurenet.sorisoopbackend.notification.domain;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationStatusRepository {
    int saveNotificationStatus(Long profileId);
    String getNotificationStatus(Long profileId);
    int updateNotificationStatus(Long profileId);
}
