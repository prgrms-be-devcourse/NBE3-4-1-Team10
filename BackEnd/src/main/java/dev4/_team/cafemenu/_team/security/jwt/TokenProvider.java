package dev4._team.cafemenu._team.security.jwt;

import dev4._team.cafemenu._team.security.user.CustomUserDetails;
import dev4._team.cafemenu._team.security.user.CustomUserDetailsService;
import dev4._team.cafemenu._team.security.redis.RedisUtil;
import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;


@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    @Value("${spring.jwt.key}")
    private String key;
    private static SecretKey secretKey;
    private final RedisUtil redisUtil;
    private final CustomUserDetailsService userDetailService;

    @PostConstruct
    private void setSecretKey() {
        secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    //  access token 발급
    public String createAccessToken(Authentication authentication, long expireTime) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireTime);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwt = Jwts.builder()
                .subject(authentication.getName())
                .claim(TokenKey.KEY_ROLE, authorities) //claims에 사용자 정보 저장
                .claim("id", customUserDetails.getUser().getId())
                .issuedAt(now) // 만들어진 시간
                .expiration(expiredDate) // 유효 기간
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
        log.info("jwt accessToken을 만드는데 성공했습니다.");
        return jwt;
    }

    // refresh token 발급
    public String createRefreshToken(Authentication authentication, long expireTime) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireTime);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwt = Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(now) // 만들어진 시간
                .claim("id", customUserDetails.getUser().getId())
                .expiration(expiredDate) // 유효 기간
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
        log.info("jwt refreshToken을 만드는데 성공했습니다.");
        return jwt;
    }

    //검증해서 값 넘겨주는 매서드
    public CustomUserDetails verify(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey) // 서명 검증
                    .build()
                    .parseSignedClaims(token) // 토큰 파싱
                    .getPayload();
            Long id = claims.get("id", Long.class);
            String role = claims.get("role", String.class);
            String email = claims.getSubject();
            log.info("id :{},role :{}, email:{}", id, role, email);
            User user = User.builder()
                    .id(id)
                    .email(email)
                    .role(UserRole.valueOf(role))
                    .build();
            return new CustomUserDetails(user);
        } catch (JwtException e) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    //남은 시간 return하는 메서드
    public long getRemainingValidity(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getExpiration().getTime() - System.currentTimeMillis();
    }
}