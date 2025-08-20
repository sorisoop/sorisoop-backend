package com.futurenet.sorisoopbackend.auth.application;

import com.futurenet.sorisoopbackend.auth.application.exception.AuthErrorCode;
import com.futurenet.sorisoopbackend.auth.application.exception.AuthException;
import com.futurenet.sorisoopbackend.auth.constant.AuthConstants;
import com.futurenet.sorisoopbackend.auth.domain.RefreshTokenRepository;
import com.futurenet.sorisoopbackend.auth.dto.request.SaveRefreshTokenRequest;
import com.futurenet.sorisoopbackend.auth.util.JwtUtil;
import com.futurenet.sorisoopbackend.auth.util.RequestUtil;
import com.futurenet.sorisoopbackend.auth.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = RequestUtil.getRefreshToken(request);

        if (refreshToken == null || jwtUtil.isExpired(refreshToken)) {
            throw new AuthException(AuthErrorCode.REFRESH_ERROR);
        }

        Long memberId = jwtUtil.getMemberId(refreshToken);
        Long profileId = jwtUtil.getProfileId(refreshToken);
        String deviceId = jwtUtil.getDeviceId(refreshToken);

        String savedRefreshToken = refreshTokenRepository.getRefreshTokenByMemberIdAndProfileIdAndDeviceId(memberId, profileId, deviceId);

        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            throw new AuthException(AuthErrorCode.REFRESH_ERROR);
        }

        String role = jwtUtil.getRole(refreshToken);

        String newAccessToken = jwtUtil.createAccessToken("access", memberId, profileId, role, AuthConstants.ACCESS_EXPIRED);
        String newRefreshToken = jwtUtil.createRefreshToken("refresh", memberId, profileId, deviceId, AuthConstants.REFRESH_EXPIRED);

        int result = refreshTokenRepository.saveRefreshToken(new SaveRefreshTokenRequest(newRefreshToken, profileId, memberId, deviceId));

        if (result == 0) {
            throw new AuthException(AuthErrorCode.TOKEN_ISSUE_FAIL);
        }

        response.addHeader("Set-Cookie", ResponseUtil.createResponseCookie("Authorization", newAccessToken, AuthConstants.ACCESS_COOKIE_EXPIRED));
        response.addHeader("Set-Cookie", ResponseUtil.createResponseCookie("refresh", newRefreshToken, AuthConstants.REFRESH_COOKIE_EXPIRED));
    }
}
