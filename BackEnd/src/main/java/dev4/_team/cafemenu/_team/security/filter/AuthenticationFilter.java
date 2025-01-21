package dev4._team.cafemenu._team.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import dev4._team.cafemenu._team.security.user.CustomUserDetails;
import dev4._team.cafemenu._team.security.user.CustomUserDetailsService;
import dev4._team.cafemenu._team.security.jwt.TokenKey;
import dev4._team.cafemenu._team.security.jwt.TokenProvider;
import dev4._team.cafemenu._team.security.redis.RedisUtil;
import dev4._team.cafemenu._team.user.dto.LoginDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final RedisUtil redisUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AuthenticationFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil, CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.redisUtil = redisUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.debug("디버그: attemptAuthentication 호출됨");
        try {
            ObjectMapper om = new ObjectMapper();
            LoginDto loginDto = om.readValue(request.getInputStream(), LoginDto.class);

            // 디버깅 로그 추가
            log.info("디버그: LoginDto - username={}, password={}", loginDto.getUsername(), loginDto.getPassword());

            if (loginDto.getUsername() == null || loginDto.getPassword() == null) {
                throw new IllegalArgumentException("username 또는 password가 누락되었습니다.");
            }

            CustomUserDetails customUserDetails;

            try {
                customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(loginDto.getUsername());
            } catch (BusinessException e) {
                throw new InternalAuthenticationServiceException(ErrorCode.NOT_FOUND_USER.getMessage(), e);
            }

            // 디버깅 로그: 데이터베이스에서 가져온 비밀번호
            log.info("디버그: 데이터베이스에서 가져온 패스워드={}", customUserDetails.getPassword());

            // Null 체크
            if (customUserDetails.getPassword() == null) {
                throw new IllegalStateException("DB에 저장된 패스워드가 null입니다.");
            }

            if (!passwordEncoder.matches(loginDto.getPassword(), customUserDetails.getPassword())) {
                throw new InternalAuthenticationServiceException(ErrorCode.INVALID_PASSWORD.getMessage());
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    customUserDetails, null, customUserDetails.getAuthorities());
            return authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            log.error("디버그: 인증 실패 - {}", e.getMessage());
            throw new InternalAuthenticationServiceException("Authentication service error", e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        // ErrorCode 설정
        ErrorCode errorCode;
        if (failed.getMessage().contains("NOT_FOUND_USER")) {
            errorCode = ErrorCode.NOT_FOUND_USER;
        } else if (failed.getMessage().contains("INVALID_PASSWORD")) {
            errorCode = ErrorCode.INVALID_PASSWORD;
        } else {
            errorCode = ErrorCode.UNAUTHORIZED;
        }

        // 응답 설정
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");

        // JSON 형태로 응답 바디 작성
        String errorMessage = String.format(
                "{\"code\": \"%s\", \"message\": \"%s\"}",
                errorCode.getErrorCode(),
                errorCode.getMessage()
        );

        // 응답 출력
        response.getWriter().write(errorMessage);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        log.debug("디버그: successfulAuthentication 호출됨");

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // Access Token 생성
        String accessToken = tokenProvider.createAccessToken(authentication, TokenKey.ACCESS_TOKEN_EXPIRE_TIME);
        // Refresh Token 생성
        String refreshToken = tokenProvider.createRefreshToken(authentication, TokenKey.REFRESH_TOKEN_EXPIRE_TIME);

        // Redis에 토큰 저장
        redisUtil.setDataExpire("RT:" + customUserDetails.getUsername(), refreshToken, TokenKey.REFRESH_TOKEN_EXPIRE_TIME / 1000);

        // 응답 설정
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=UTF-8");

        String successResponse = String.format(
                "{\"status\": %d, \"message\": \"%s\", \"data\": {\"accessToken\": \"%s\", \"refreshToken\": \"%s\"}}",
                HttpServletResponse.SC_OK,
                "로그인 성공",
                accessToken,
                refreshToken
        );

        response.getWriter().write(successResponse);
    }
}
