package dev4._team.cafemenu._team.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "1", "서버내부 에러가 발생했습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "11", "유저를 찾을수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"12","비밀번호가 틀렸습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"13","인증이 필요합니다.");



    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}

