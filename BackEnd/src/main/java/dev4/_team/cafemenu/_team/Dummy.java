package dev4._team.cafemenu._team;

import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.entity.UserRole;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class Dummy {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // 더미 유저 데이터가 이미 있다면 추가하지 않음
        if (userRepository.count() > 0) {
            return;
        }

        User normalUser = User.builder()
                .email("user@naver.com")
                .nickname("테스트")
                .password(passwordEncoder.encode("Dbwjdlqslek12!"))
                .role(UserRole.USER)
                .build();

        // 데이터베이스에 저장
        userRepository.save(normalUser);

        System.out.println("더미 데이터가 초기화되었습니다.");
    }
}