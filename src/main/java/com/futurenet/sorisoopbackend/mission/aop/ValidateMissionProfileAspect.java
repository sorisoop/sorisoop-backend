package com.futurenet.sorisoopbackend.mission.aop;

import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.mission.application.exception.MissionErrorCode;
import com.futurenet.sorisoopbackend.mission.application.exception.MissionException;
import com.futurenet.sorisoopbackend.profile.domain.ProfileRepository;
import com.futurenet.sorisoopbackend.profile.domain.Role;
import com.futurenet.sorisoopbackend.profile.dto.response.FindProfileResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidateMissionProfileAspect {
    private final ProfileRepository profileRepository;

    @Before("@annotation(com.futurenet.sorisoopbackend.mission.aop.ValidateMissionProfile)")
    public void validateProfile() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long profileId = userPrincipal.getProfileId();

        FindProfileResponse profile = profileRepository.getProfileByProfileId(profileId);

        if (profile == null) {
            throw new MissionException(MissionErrorCode.PROFILE_NOT_FOUND);
        }

        if (profile.getRole() != Role.PARENT) {
            throw new MissionException(MissionErrorCode.PROFILE_NOT_PARENT);
        }
    }
}
