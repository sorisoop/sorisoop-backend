package com.futurenet.sorisoopbackend.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SignupRequest {
    private Long id;
    private String name;
    private String birth;
    private String email;
    private String password;
}
