package dev4._team.cafemenu._team.config;

import dev4._team.cafemenu._team.security.CustomAuthenticationProvider;
import dev4._team.cafemenu._team.security.CustomFailureHandler;
import dev4._team.cafemenu._team.security.CustomSuccessHandler;
import dev4._team.cafemenu._team.security.CustomUserDetailsService;
import dev4._team.cafemenu._team.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailureHandler customFailureHandler;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/admin/**").hasRole(UserRole.ADMIN.name()) // 관리자 권한
                        .anyRequest().permitAll() // 나머지 요청 허용
                )
                //현재는 spring security를 통해서 로그인을하고 토큰을 도입하면서 custom으로 바꿀 예정
                .formLogin(form -> form
                        .loginProcessingUrl("/login-process") // 로그인 처리 경로
                        .successHandler(customSuccessHandler) // 성공 핸들러
                        .failureHandler(customFailureHandler) // 실패 핸들러
                        .permitAll() // 로그인 요청 허용
                )
                .httpBasic(AbstractHttpConfigurer::disable); // HTTP Basic 비활성화
        return http.build();
    }

    //authenticationManager를 custom으로 만들어서 직접 처리할수있도록 등록
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenticationProvider)
                .build();
    }
}
