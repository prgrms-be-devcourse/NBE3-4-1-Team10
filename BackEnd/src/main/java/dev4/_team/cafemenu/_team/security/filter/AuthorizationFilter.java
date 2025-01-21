package dev4._team.cafemenu._team.security.filter;

import dev4._team.cafemenu._team.security.user.CustomUserDetails;
import dev4._team.cafemenu._team.security.jwt.TokenKey;
import dev4._team.cafemenu._team.security.jwt.TokenProvider;
import dev4._team.cafemenu._team.security.redis.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;


@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;

    public AuthorizationFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider, RedisUtil redisUtil) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
        this.redisUtil = redisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Swagger 경로는 필터 제외
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        if (!isHeaderVerify(request)) {
            log.warn("Authorization 헤더가 유효하지 않습니다.");
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(TokenKey.AUTHORIZATION_HEADER).replace(TokenKey.TOKEN_PREFIX, "");
        log.debug("Authorization 헤더에서 토큰 추출: {}", token);


        String redisBlacklistKey = "BL:" + token;
        if (redisUtil.getData(redisBlacklistKey) != null) {
            log.warn("요청된 토큰은 블랙리스트에 등록되어 있습니다: {}", redisBlacklistKey);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"message\": \"유효하지 않은 토큰입니다.\"}");
            return;
        }

        try {
            // 토큰 검증
            CustomUserDetails customUserDetails = tokenProvider.verify(token);
            log.debug("토큰 검증 성공. 사용자 이름: {}", customUserDetails.getUsername());
            log.debug("사용자 권한: {}", customUserDetails.getAuthorities());

            // 인증 객체 생성 및 SecurityContext 설정
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    customUserDetails, null, customUserDetails.getAuthorities());
            log.info("autehntication :{}, email :{}",authentication,authentication.getName());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
            if (currentAuth == null) {
                log.warn("SecurityContext에 인증 객체가 없습니다.");
            } else {
                log.info("SecurityContext 인증 객체: {}", currentAuth);
                log.info("SecurityContext 권한: {}", currentAuth.getAuthorities());
            }
        } catch (Exception e) {
            log.error("토큰 검증 실패: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"message\": \"유효하지 않은 토큰입니다.\"}");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isHeaderVerify(HttpServletRequest request) {
        String header = request.getHeader(TokenKey.AUTHORIZATION_HEADER);
        if (header == null || !header.startsWith(TokenKey.TOKEN_PREFIX)) {
            log.warn("유효하지 않은 Authorization 헤더: {}", header);
            return false;
        }
        return true;
    }
}

