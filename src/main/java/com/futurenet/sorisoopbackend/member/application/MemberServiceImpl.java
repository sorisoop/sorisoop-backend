package com.futurenet.sorisoopbackend.member.application;

import com.futurenet.sorisoopbackend.member.domain.MemberRepository;
import com.futurenet.sorisoopbackend.member.dto.response.FindMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public FindMemberResponse getMemberByEmail(String email) {
        return memberRepository.getMemberByEmail(email);
    }
}
