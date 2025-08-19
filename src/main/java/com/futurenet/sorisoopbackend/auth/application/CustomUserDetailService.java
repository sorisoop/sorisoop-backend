package com.futurenet.sorisoopbackend.auth.application;

import com.futurenet.sorisoopbackend.auth.dto.UserAuthDto;
import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.member.domain.MemberRepository;
import com.futurenet.sorisoopbackend.member.dto.response.FindMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        FindMemberResponse response = memberRepository.getMemberByEmail(email);

        if (response == null) {
            throw new UsernameNotFoundException("해당 이메일로 가입된 사용자가 없습니다.");
        }

        UserAuthDto userAuthDto = new UserAuthDto(response.getEmail(), response.getPassword(), response.getId());

        return new UserPrincipal(userAuthDto);
    }
}
