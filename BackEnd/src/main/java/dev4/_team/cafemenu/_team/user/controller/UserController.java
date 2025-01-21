package dev4._team.cafemenu._team.user.controller;

import dev4._team.cafemenu._team.security.jwt.TokenKey;
import dev4._team.cafemenu._team.security.jwt.TokenProvider;
import dev4._team.cafemenu._team.security.redis.RedisUtil;
import dev4._team.cafemenu._team.user.dto.LoginDto;
import dev4._team.cafemenu._team.user.dto.LoginResponseDto;
import dev4._team.cafemenu._team.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import dev4._team.cafemenu._team.user.dto.SignupDto;
import dev4._team.cafemenu._team.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
  
    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "사용자가 로그인을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "유효하지 않은 비밀번호 || 인증 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "서버 내부 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginDto) {
        LoginResponseDto response = userService.login(loginDto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/logout")
    @Operation(
            summary = "로그 아웃",
            description = "사용자가 로그아웃을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
                    @ApiResponse(responseCode = "404", description = "토큰이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "로그아웃 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<?> logout(@RequestParam(value = "accessToken", required = false) String accessToken) {
        // 서비스에서 처리한 결과를 그대로 응답
        userService.logout(accessToken);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/check-auth")
    @Operation(
            summary = "로그인 상태 확인",
            description = "현재 사용자가 인증 상태인지 확인합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<String> checkAuth(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("사용자가 인증되지 않았습니다.");
        }
        log.info("현재 인증된 사용자: {}", authentication);
        log.info("현재 권한: {}", authentication.getAuthorities());
        return ResponseEntity.ok("로그인 상태입니다. 사용자: " + authentication.getName());
    }
  
    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupDto signupDto) {
        userService.signup(signupDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }
}
