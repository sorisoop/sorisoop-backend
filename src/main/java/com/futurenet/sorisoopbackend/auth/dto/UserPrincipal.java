package com.futurenet.sorisoopbackend.auth.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserPrincipal implements OAuth2User, UserDetails {

    private final UserAuthDto userAuthDto;

    public UserPrincipal(UserAuthDto userAuthDto) {
        this.userAuthDto = userAuthDto;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userAuthDto.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return userAuthDto.getPassword();
    }

    @Override
    public String getUsername() {
        return userAuthDto.getEmail();
    }

    @Override
    public String getName() {
        return null;
    }

    public Long getId() {
        return userAuthDto.getMemberId();
    }

    public Long getProfileId() {
        return userAuthDto.getProfileId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
