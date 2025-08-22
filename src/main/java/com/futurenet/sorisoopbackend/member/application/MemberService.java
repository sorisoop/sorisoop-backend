package com.futurenet.sorisoopbackend.member.application;

import com.futurenet.sorisoopbackend.member.dto.response.CustomerKeyResponse;
import com.futurenet.sorisoopbackend.member.dto.response.FindMemberResponse;

public interface MemberService {
    FindMemberResponse getMemberByEmail(String email);
    CustomerKeyResponse getCustomerKeyByMemberId(Long memberId);
}
