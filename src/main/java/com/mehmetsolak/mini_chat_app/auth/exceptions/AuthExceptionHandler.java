package com.mehmetsolak.mini_chat_app.auth.exceptions;

import com.mehmetsolak.mini_chat_app.common.exception.ErrorResponse;
import com.mehmetsolak.mini_chat_app.common.message.InternationalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class AuthExceptionHandler {

    private final InternationalizationService i18nService;

    @SuppressWarnings("unused")
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException() {
        String message = i18nService.getMessage("auth.login.badCredentials");
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
