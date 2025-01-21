package dev4._team.cafemenu._team.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
        response.setContentType("application/json; charset=UTF-8");

        String errorMessage = String.format(
                "{\"status\": %d, \"message\": \"%s\"}",
                HttpServletResponse.SC_FORBIDDEN,
                "권한이 없습니다"
        );

        response.getWriter().write(errorMessage);
    }
}
