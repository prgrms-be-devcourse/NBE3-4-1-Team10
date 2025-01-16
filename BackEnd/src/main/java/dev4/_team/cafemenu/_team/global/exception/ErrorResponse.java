package dev4._team.cafemenu._team.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String errorCode;
    private String message;

    public static ErrorResponse of(BusinessException e) {
        return new ErrorResponse(
                e.getErrorCode().getHttpStatus(),
                e.getErrorCode().getErrorCode(),
                e.getMessage()
        );
    }
}