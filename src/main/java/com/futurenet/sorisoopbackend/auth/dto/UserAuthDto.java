package com.futurenet.sorisoopbackend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAuthDto {
    private String email;
    private String password;
    private Long memberId;
    private Long profileId;
    private String role;

    public UserAuthDto(Long memberId, String email, String role) {
        this.memberId = memberId;
        this.email = email;
        this.role = role;
    }

    public UserAuthDto(Long memberId, Long profileId, String role) {
        this.memberId = memberId;
        this.profileId = profileId;
        this.role = role;
    }

    public UserAuthDto(String email, String password, Long memberId) {
        this.email = email;
        this.password = password;
        this.memberId = memberId;
    }
}
