package com.futurenet.sorisoopbackend.auth.filter;

import com.futurenet.sorisoopbackend.auth.util.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        if (!requestURI.equals("/auth/logout") || !request.getMethod().equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        response.addHeader("Set-Cookie", ResponseUtil.createResponseCookie("Authorization", null, 0));
        response.addHeader("Set-Cookie", ResponseUtil.createResponseCookie("refresh", null, 0));

        ResponseUtil.setResponse(response, "AU101", "로그아웃 성공", HttpStatus.OK);
    }
}
