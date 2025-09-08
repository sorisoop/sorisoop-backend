package com.futurenet.sorisoopbackend.member.application;

import com.futurenet.sorisoopbackend.member.application.exception.MemberErrorCode;
import com.futurenet.sorisoopbackend.member.application.exception.MemberException;
import com.futurenet.sorisoopbackend.member.domain.MemberRepository;
import com.futurenet.sorisoopbackend.member.dto.response.CustomerKeyResponse;
import com.futurenet.sorisoopbackend.member.dto.response.FindMemberResponse;
import com.futurenet.sorisoopbackend.member.util.CustomerKeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public boolean getMemberByEmail(String email) {
        FindMemberResponse response = memberRepository.getMemberByEmail(email);
        return response == null;
    }

    @Override
    public CustomerKeyResponse getCustomerKeyByMemberId(Long memberId) {
        String customerKey = memberRepository.getCustomerKeyByMemberId(memberId);
        if (customerKey != null) return new CustomerKeyResponse(customerKey);

        String newKey = CustomerKeyUtil.generate(memberId);
        int updated = memberRepository.updateCustomerKey(newKey, memberId);
        if (updated <= 0) throw new MemberException(MemberErrorCode.CUSTOMER_KEY_UPDATE_FAIL);

        return new CustomerKeyResponse(newKey);
    }



}
