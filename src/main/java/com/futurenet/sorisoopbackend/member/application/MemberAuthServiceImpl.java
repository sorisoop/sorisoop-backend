package com.futurenet.sorisoopbackend.member.application;

import com.futurenet.sorisoopbackend.member.application.exception.MemberErrorCode;
import com.futurenet.sorisoopbackend.member.application.exception.MemberException;
import com.futurenet.sorisoopbackend.member.domain.MemberRepository;
import com.futurenet.sorisoopbackend.member.dto.request.OAuthSignupRequest;
import com.futurenet.sorisoopbackend.member.dto.request.SignupRequest;
import com.futurenet.sorisoopbackend.member.dto.response.FindMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberAuthServiceImpl implements MemberAuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public Long oAuthSignup(OAuthSignupRequest request) {

        FindMemberResponse member = memberRepository.getMemberByEmail(request.getEmail());

        if (member != null) {
            throw new MemberException(MemberErrorCode.ALREADY_EXIST);
        }

        int result = memberRepository.saveOAuthMember(request);

        if (result == 0) {
            throw new MemberException(MemberErrorCode.SIGNUP_FAIL);
        }

        return request.getId();
    }

    @Override
    @Transactional
    public void signup(SignupRequest request) {
        FindMemberResponse member = memberRepository.getMemberByEmail(request.getEmail());

        if (member != null) {
            throw new MemberException(MemberErrorCode.ALREADY_EXIST);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        int result = memberRepository.saveMember(request);

        if (result == 0) {
            throw new MemberException(MemberErrorCode.SIGNUP_FAIL);
        }
    }
}