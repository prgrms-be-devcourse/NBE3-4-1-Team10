package dev4._team.cafemenu._team.config;

import dev4._team.cafemenu._team.security.filter.AuthenticationFilter;
import dev4._team.cafemenu._team.security.filter.AuthorizationFilter;
import dev4._team.cafemenu._team.security.filter.CustomAuthenticationProvider;
import dev4._team.cafemenu._team.security.handler.CustomAccessDeniedHandler;
import dev4._team.cafemenu._team.security.handler.CustomAuthenticationEntryPoint;
import dev4._team.cafemenu._team.security.jwt.TokenProvider;
import dev4._team.cafemenu._team.security.redis.RedisUtil;
import dev4._team.cafemenu._team.security.user.CustomUserDetailsService;
import dev4._team.cafemenu._team.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final TokenProvider tokenProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final RedisUtil redisUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * AuthenticationManager를 Bean으로 등록
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenticationProvider) // Custom Authentication Provider 등록
                .build();
    }

    /**
     * Security Filter Chain 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        // HTTP 보안 설정

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, redisUtil, customUserDetailsService, passwordEncoder, tokenProvider);

        http.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class);

        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(configurationSource()));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        // JwtAuthorizationFilter 추가
        AuthorizationFilter jwtAuthorizationFilter = new AuthorizationFilter(authenticationManager, tokenProvider, redisUtil);
        http.addFilterAfter(jwtAuthorizationFilter, AuthenticationFilter.class);

        // 인증 및 권한 에러 처리 핸들러
        http.exceptionHandling(exceptionHandling -> {
            exceptionHandling
                    .authenticationEntryPoint(customAuthenticationEntryPoint) // 인증 실패 처리
                    .accessDeniedHandler(customAccessDeniedHandler); // 권한 실패 처리
        });

        // 경로 권한 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/admin/**").hasRole(UserRole.ADMIN.name()) // 관리자 권한
                .anyRequest().permitAll() // 나머지 요청 허용
        );

        return http.build();
    }

    /**
     * CORS 설정
     */
    public CorsConfigurationSource configurationSource() {
        log.debug("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedOriginPattern("*"); // 모든 Origin 허용
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 허용
        configuration.addExposedHeader("Authorization"); // 헤더 노출
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
