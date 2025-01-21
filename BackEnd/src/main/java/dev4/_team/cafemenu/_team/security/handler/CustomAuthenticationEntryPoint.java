package dev4._team.cafemenu._team.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("application/json; charset=UTF-8");

        String errorMessage = String.format(
                "{\"status\": %d, \"message\": \"%s\"}",
                HttpServletResponse.SC_UNAUTHORIZED,
                "로그인을 진행해 주세요"
        );

        response.getWriter().write(errorMessage);
    }
}
