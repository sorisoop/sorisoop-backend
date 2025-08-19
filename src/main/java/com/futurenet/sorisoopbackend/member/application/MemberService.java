package com.futurenet.sorisoopbackend.member.application;

import com.futurenet.sorisoopbackend.member.dto.response.FindMemberResponse;

public interface MemberService {
    FindMemberResponse getMemberByEmail(String email);
}
