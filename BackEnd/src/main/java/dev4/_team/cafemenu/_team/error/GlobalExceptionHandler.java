package dev4._team.cafemenu._team.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //나머지 에러들
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus(), ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
