package com.futurenet.sorisoopbackend.member.application;

import com.futurenet.sorisoopbackend.member.dto.response.CustomerKeyResponse;

public interface MemberService {
    boolean getMemberByEmail(String email);
    CustomerKeyResponse getCustomerKeyByMemberId(Long memberId);
}
