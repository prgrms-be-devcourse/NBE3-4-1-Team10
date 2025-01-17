package dev4._team.cafemenu._team.security;

import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        ErrorCode errorCode;

        if (exception.getMessage().contains(ErrorCode.NOT_FOUND_USER.getMessage())) {
            errorCode = ErrorCode.NOT_FOUND_USER;
        } else if (exception.getMessage().contains(ErrorCode.INVALID_PASSWORD.getMessage())) {
            errorCode = ErrorCode.INVALID_PASSWORD;
        } else {
            errorCode = ErrorCode.UNAUTHORIZED;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        String errorMessage = String.format(
                "{\"code\": \"%s\", \"message\": \"%s\"}",
                errorCode.getErrorCode(),
                errorCode.getMessage()
        );
        response.getWriter().write(errorMessage);
    }
}
