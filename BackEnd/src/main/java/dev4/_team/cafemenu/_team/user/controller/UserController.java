package dev4._team.cafemenu._team.user.controller;

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

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupDto signupDto) {
        userService.signup(signupDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }
}
