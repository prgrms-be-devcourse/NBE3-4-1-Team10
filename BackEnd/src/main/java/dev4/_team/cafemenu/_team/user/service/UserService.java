package dev4._team.cafemenu._team.user.service;

import dev4._team.cafemenu._team.user.dto.SignupDto;
import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입 처리
    public void signup(SignupDto signupDto) {
        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());

        User user = new User(signupDto.getEmail(), signupDto.getNickname(), encodedPassword);

        // 이메일 중복 체크
        if (userRepository.existsByEmail(signupDto.getEmail())) {  
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        userRepository.save(user);
    }
}
