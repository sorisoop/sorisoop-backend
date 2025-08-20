package com.futurenet.sorisoopbackend.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String name;

    @NotNull
    private String birth;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}
