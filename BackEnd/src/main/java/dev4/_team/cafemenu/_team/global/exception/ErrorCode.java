package dev4._team.cafemenu._team.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "1", "서버내부 에러가 발생했습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "2", "해당 주문을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "3", "사용자를 찾을 수 없습니다."),
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "21", "상품을 찾을 수 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "11", "유저를 찾을수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"12","비밀번호가 틀렸습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"13","인증이 필요합니다."),
    NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, "14", "토큰이 존재하지 않습니다."),
    FAIL_LOGOUT(HttpStatus.INTERNAL_SERVER_ERROR, "15", "로그아웃에 실패했습니다."),
    EXIST_USER(HttpStatus.CONFLICT,"16","이미 존재하는 회원입니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}

