package com.futurenet.sorisoopbackend.member.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindMemberResponse {
    private Long id;
    private String email;
    private String password;
    private String provider;
}
