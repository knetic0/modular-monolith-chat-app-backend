package com.mehmetsolak.mini_chat_app.auth.exceptions;

import com.mehmetsolak.mini_chat_app.common.exception.ErrorResponse;
import com.mehmetsolak.mini_chat_app.common.message.InternationalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class AuthExceptionHandler {

    private final InternationalizationService i18nService;

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
            InvalidCredentialsException exception
    ) {
        String message = i18nService.getMessage(exception.getMessage());
        ErrorResponse response = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                message,
                null
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }
}
