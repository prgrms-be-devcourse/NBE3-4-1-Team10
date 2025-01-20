package dev4.team.cafemenu.team.user.controller;

import dev4.team.cafemenu.team.user.dto.SignupDto;
import dev4.team.cafemenu.team.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupDto signupDto) {
        userService.signup(signupDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }
}
