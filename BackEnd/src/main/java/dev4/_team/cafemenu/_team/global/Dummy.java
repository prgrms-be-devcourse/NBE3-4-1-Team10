package dev4._team.cafemenu._team.global;

import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.entity.UserRole;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class Dummy {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        User user = User.builder()
                .email("testm@naver.com")
                .nickname("사용자")
                .password(passwordEncoder.encode("Rkdwjdtn12!"))
                .role(UserRole.USER)
                .build();
        userRepository.save(user);

        User adminUser = User.builder()
                .email("admin@naver.com")
                .nickname("관리자")
                .password(passwordEncoder.encode("Rhksflwk12!"))
                .role(UserRole.ADMIN)
                .build();
        userRepository.save(adminUser);
    }
}