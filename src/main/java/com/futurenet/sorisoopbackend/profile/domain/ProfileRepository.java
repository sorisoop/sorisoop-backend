package com.futurenet.sorisoopbackend.profile.domain;

import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProfileRepository {
    List<FindProfileResponse> findAllProfilesByMemberId(Long memberId);
}
