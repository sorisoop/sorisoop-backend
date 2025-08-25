package com.futurenet.sorisoopbackend.member.domain;

import com.futurenet.sorisoopbackend.member.dto.request.OAuthSignupRequest;
import com.futurenet.sorisoopbackend.member.dto.request.SignupRequest;
import com.futurenet.sorisoopbackend.member.dto.response.CustomerKeyResponse;
import com.futurenet.sorisoopbackend.member.dto.response.FindMemberResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberRepository {
    int saveOAuthMember(@Param("request") OAuthSignupRequest request);
    int saveMember(@Param("request") SignupRequest request);
    FindMemberResponse getMemberByEmail(@Param("email") String email);
    String getCustomerKeyByMemberId(@Param("memberId") Long memberId);
    int updateCustomerKey(@Param("memberId") Long memberId, @Param("customerKey") String customerKey);
}
