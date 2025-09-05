package com.futurenet.sorisoopbackend.auth.oauth2.handler;

import com.futurenet.sorisoopbackend.auth.constant.AuthConstants;
import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.auth.util.JwtUtil;
import com.futurenet.sorisoopbackend.auth.util.RequestUtil;
import com.futurenet.sorisoopbackend.auth.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        Long memberId = userPrincipal.getId();
        String role = auth.getAuthority();

        String deviceId = RequestUtil.getDeviceId(request);

        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString();
            response.addHeader("Set-Cookie", ResponseUtil.createResponseCookie("deviceId", deviceId, AuthConstants.DEVICE_ID_COOKIE_EXPIRED));
        }

        String accessToken = jwtUtil.createAccessToken("access", memberId, null, role, AuthConstants.ACCESS_EXPIRED);

        response.addHeader("Set-Cookie", ResponseUtil.createResponseCookie("Authorization", accessToken, AuthConstants.ACCESS_COOKIE_EXPIRED));

        response.sendRedirect("https://sorisoop.n-e.kr/profile");
      //  response.sendRedirect("http://localhost:5173/profile");
    }
}
