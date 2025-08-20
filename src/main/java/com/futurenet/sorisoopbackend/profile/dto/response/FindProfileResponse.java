package com.futurenet.sorisoopbackend.profile.dto.response;

import com.futurenet.sorisoopbackend.profile.domain.Gender;
import com.futurenet.sorisoopbackend.profile.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindProfileResponse {
    private Long id;
    private String nickname;
    private String profileImage;
    private Role role;
    private int age;
    private Gender gender;
}
