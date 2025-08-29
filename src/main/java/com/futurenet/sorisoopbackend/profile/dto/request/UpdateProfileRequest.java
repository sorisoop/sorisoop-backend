package com.futurenet.sorisoopbackend.profile.dto.request;

import com.futurenet.sorisoopbackend.profile.domain.Gender;
import com.futurenet.sorisoopbackend.profile.domain.Role;
import com.futurenet.sorisoopbackend.profile.dto.UpdateProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateProfileRequest {
    private MultipartFile profileImage;
    private Long profileId;
    private String nickname;
    private Role role;
    private Integer age;
    private Gender gender;

    public UpdateProfileDto toDto(String profileImage) {
        return UpdateProfileDto.builder()
                .id(profileId)
                .nickname(nickname)
                .role(role)
                .age(age)
                .gender(gender)
                .profileImage(profileImage)
                .build();
    }
}
