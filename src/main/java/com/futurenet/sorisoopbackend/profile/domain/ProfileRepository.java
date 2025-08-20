package com.futurenet.sorisoopbackend.profile.domain;

import com.futurenet.sorisoopbackend.profile.dto.SaveProfileDto;
import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProfileRepository {
    List<FindProfileResponse> findAllProfilesByMemberId(Long memberId);
    int saveProfile(@Param("request") SaveProfileDto request);
    boolean existsProfileByMemberIdAndProfileId(Long memberId, Long profileId);
}
