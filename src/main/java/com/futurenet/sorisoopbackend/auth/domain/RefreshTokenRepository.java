package com.futurenet.sorisoopbackend.auth.domain;

import com.futurenet.sorisoopbackend.auth.dto.request.SaveRefreshTokenRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RefreshTokenRepository {
    void saveRefreshToken(@Param("request") SaveRefreshTokenRequest request);
    String getRefreshTokenByMemberIdAndProfileIdAndDeviceId(Long memberId, Long profileId, String deviceId);
}
