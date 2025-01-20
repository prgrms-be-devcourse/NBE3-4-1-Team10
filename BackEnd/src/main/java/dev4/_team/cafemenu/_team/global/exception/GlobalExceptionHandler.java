package dev4._team.cafemenu._team.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 직접 만든 에러들
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.of(e));
    }

    // 로그인 비밀번호 틀릴때
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity
                .status(ErrorCode.INVALID_PASSWORD.getHttpStatus())
                .body(new ErrorResponse(
                        ErrorCode.INVALID_PASSWORD.getHttpStatus(),
                        ErrorCode.INVALID_PASSWORD.getErrorCode(),
                        ErrorCode.INVALID_PASSWORD.getMessage()
                ));
    }

    //로그인 아이디 틀릴때
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity
                .status(ErrorCode.NOT_FOUND_USER.getHttpStatus())
                .body(new ErrorResponse(
                        ErrorCode.NOT_FOUND_USER.getHttpStatus(),
                        ErrorCode.NOT_FOUND_USER.getErrorCode(),
                        ErrorCode.NOT_FOUND_USER.getMessage()
                ));
    }

    //나머지 에러들
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus(), ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
