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
import org.springframework.http.HttpMethod;
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
@RequiredArgsConstructor
@EnableWebSecurity
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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, redisUtil, customUserDetailsService, passwordEncoder, tokenProvider);
        http.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class);

        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(configurationSource()));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        AuthorizationFilter jwtAuthorizationFilter = new AuthorizationFilter(authenticationManager, tokenProvider, redisUtil);
        http.addFilterAfter(jwtAuthorizationFilter, AuthenticationFilter.class);

        http.exceptionHandling(exceptionHandling -> {
            exceptionHandling
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler);
        });

        // 경로 권한 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/api/product").permitAll()
                .requestMatchers("/swagger-ui/**", "api-docs/**", "/v3/api-docs/**", "/api/user/signup", "/api/user/login").permitAll() // 회원가입 및 로그인 허용
                .requestMatchers("/api/admin/**").hasAuthority(UserRole.ROLE_ADMIN.name()) // 관리자 권한 필요
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
        );

        return http.build();
    }


    /**
     * CORS 설정
     */
    public CorsConfigurationSource configurationSource() {
        log.debug("CORS 설정 등록 중");
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
