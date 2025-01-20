package dev4._team.cafemenu._team.user.service;

import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import dev4._team.cafemenu._team.security.user.CustomUserDetails;
import dev4._team.cafemenu._team.security.jwt.TokenKey;
import dev4._team.cafemenu._team.security.jwt.TokenProvider;
import dev4._team.cafemenu._team.security.redis.RedisUtil;
import dev4._team.cafemenu._team.user.dto.LoginDto;
import dev4._team.cafemenu._team.user.dto.LoginResponseDto;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import dev4._team.cafemenu._team.user.dto.SignupDto;
import dev4._team.cafemenu._team.user.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginDto loginDto) {
        // 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );

        // 인증된 사용자 정보로 토큰 생성
        String accessToken = tokenProvider.createAccessToken(authentication, TokenKey.ACCESS_TOKEN_EXPIRE_TIME);
        String refreshToken = tokenProvider.createRefreshToken(authentication, TokenKey.REFRESH_TOKEN_EXPIRE_TIME);

        // Redis에 Refresh Token 저장
        redisUtil.setDataExpire("RT:" + authentication.getName(), refreshToken, TokenKey.REFRESH_TOKEN_EXPIRE_TIME / 1000);

        return new LoginResponseDto(accessToken, refreshToken);
    }

    public void logout(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_TOKEN);
        }

        try {
            // Access Token 검증 및 사용자 정보 확인
            CustomUserDetails customUserDetails = tokenProvider.verify(accessToken);
            log.info("Access Token 검증 완료. 사용자: {}", customUserDetails.getUsername());

            // Redis에서 Refresh Token 삭제
            String redisRefreshTokenKey = "RT:" + customUserDetails.getUsername();
            if (redisUtil.getData(redisRefreshTokenKey) != null) {
                redisUtil.deleteData(redisRefreshTokenKey);
                log.info("Redis에서 Refresh Token 삭제 완료: {}", redisRefreshTokenKey);
            }

            // Access Token 블랙리스트에 추가 (남은 유효 시간 동안)
            long remainingTime = tokenProvider.getRemainingValidity(accessToken);
            String redisBlacklistKey = "BL:" + accessToken;
            redisUtil.setDataExpire(redisBlacklistKey, "logged_out", remainingTime / 1000);
            log.info("Access Token 블랙리스트 등록 완료: {}", redisBlacklistKey);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.FAIL_LOGOUT);
        }
    }

    // 회원가입 처리
    public void signup(SignupDto signupDto) {
        User user = signupDto.toUserEntity(passwordEncoder);
        // 이메일 중복 체크
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new BusinessException(ErrorCode.EXIST_USER);
        }
        userRepository.save(user);
    }
}
