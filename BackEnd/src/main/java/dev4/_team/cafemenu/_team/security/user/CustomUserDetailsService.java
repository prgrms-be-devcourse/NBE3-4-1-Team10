package dev4._team.cafemenu._team.security;

import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import dev4._team.cafemenu._team.user.entity.User;
import dev4._team.cafemenu._team.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUser(email);
        return new CustomUserDetails(user);
    }

    private User getUser(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        log.info("User를 찾았습니다. email : {}, nickname : {}", user.getEmail(), user.getNickname());
        return user;
    }
}
