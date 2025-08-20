package com.futurenet.sorisoopbackend.profile.dto.request;

import com.futurenet.sorisoopbackend.profile.domain.Gender;
import com.futurenet.sorisoopbackend.profile.domain.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SaveProfileRequest {
    private MultipartFile profileImage;
    @NotNull
    private String nickname;
    @NotNull
    private Role role;
    private int age;
    private Gender gender;
}
