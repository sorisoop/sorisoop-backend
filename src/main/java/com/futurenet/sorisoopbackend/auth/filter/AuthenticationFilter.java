package com.futurenet.sorisoopbackend.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurenet.sorisoopbackend.auth.constant.AuthConstants;
import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.auth.dto.request.LoginRequest;
import com.futurenet.sorisoopbackend.auth.util.JwtUtil;
import com.futurenet.sorisoopbackend.auth.util.RequestUtil;
import com.futurenet.sorisoopbackend.auth.util.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginRequest loginRequest;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            loginRequest = objectMapper.readValue(messageBody, LoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserPrincipal userPrincipal = (UserPrincipal) authResult.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        Long memberId = userPrincipal.getId();
        String deviceId = RequestUtil.getDeviceId(request);

        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString();
            response.addHeader("Set-Cookie", ResponseUtil.createResponseCookie("deviceId", deviceId, AuthConstants.DEVICE_ID_COOKIE_EXPIRED));
        }

        String accessToken = jwtUtil.createAccessToken("access", memberId, null, role, AuthConstants.ACCESS_EXPIRED);

        response.addHeader("Set-Cookie", ResponseUtil.createResponseCookie("Authorization", accessToken, AuthConstants.ACCESS_COOKIE_EXPIRED));

        ResponseUtil.setResponse(response, "AU102", "일반 로그인 성공", HttpStatus.OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.setResponse(response, "AU001", "일반 로그인 실패", HttpStatus.UNAUTHORIZED);
    }
}
