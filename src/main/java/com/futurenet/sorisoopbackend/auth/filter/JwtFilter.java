package com.futurenet.sorisoopbackend.auth.filter;

import com.futurenet.sorisoopbackend.auth.constant.AuthConstants;
import com.futurenet.sorisoopbackend.auth.dto.UserAuthDto;
import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.auth.util.JwtUtil;
import com.futurenet.sorisoopbackend.auth.util.RequestUtil;
import com.futurenet.sorisoopbackend.auth.util.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = RequestUtil.getAccessToken(request);
        String deviceId = RequestUtil.getDeviceId(request);

        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString();
            response.addHeader("Set-Cookie", ResponseUtil.createResponseCookie("deviceId", deviceId, AuthConstants.DEVICE_ID_COOKIE_EXPIRED));
        }

        if (accessToken != null && !jwtUtil.isExpired(accessToken)) {
            if ("access".equals(jwtUtil.getCategory(accessToken))) {
                Long memberId = jwtUtil.getMemberId(accessToken);
                Long profileId = jwtUtil.getProfileId(accessToken);
                String role = jwtUtil.getRole(accessToken);

                UserPrincipal userPrincipal = new UserPrincipal(new UserAuthDto(memberId, profileId, role));
                Authentication auth = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
