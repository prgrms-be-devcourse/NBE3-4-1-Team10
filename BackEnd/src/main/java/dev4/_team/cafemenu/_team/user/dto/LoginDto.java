package dev4._team.cafemenu._team.user.dto;

import dev4._team.cafemenu._team.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotEmpty
    @Email(message = "이메일 형식에 맞지 않습니다.", regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @Schema(description = "이메일, 이메일 형식 검사를 진행, 그이외는 오류", example = "testm@naver.com")
    private String username;

    @NotEmpty
    @Schema(description = "비밀번호, 대문자+소문자 조합, 특수문자 + 숫자 조합, 연속된 문자 검사를 진행, 그이외는 오류", example = "Rkdwjdtn12!")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])(?!.*(.)\\1{2,}).{8,}$",
            message = "패스워드는 비밀번호, 대문자+소문자 조합, 특수문자 + 숫자 조합, 연속된 문자 3개이상 금지입니다"
    )
    @Size(min = 8, max = 19, message = "비밀번호는 8자 이상 20자 미만입니다.")
    private String password;

    public static LoginDto of(User user) {
        return LoginDto.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
