package com.futurenet.sorisoopbackend.profile.dto;

import com.futurenet.sorisoopbackend.profile.domain.Gender;
import com.futurenet.sorisoopbackend.profile.domain.Role;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveProfileDto {
    private String profileImage;
    private Long memberId;
    private String nickname;
    private Role role;
    private Integer age;
    private Gender gender;
}
