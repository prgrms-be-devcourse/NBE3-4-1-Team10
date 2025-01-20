package dev4._team.cafemenu._team.security;

import dev4._team.cafemenu._team.global.exception.BusinessException;
import dev4._team.cafemenu._team.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails user;

        //로그인을 할때 이상이없으면 loadUserByUsername을 통해서 UserDetails객체를 생성함
        try {
            user = userDetailsService.loadUserByUsername(username);
        } catch (BusinessException e) {
            throw new UsernameNotFoundException(e.getErrorCode().getMessage());
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(new BusinessException(ErrorCode.INVALID_PASSWORD).getMessage());
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
