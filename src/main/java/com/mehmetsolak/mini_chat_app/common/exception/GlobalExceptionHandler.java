package com.mehmetsolak.mini_chat_app.common.exception;

import com.mehmetsolak.mini_chat_app.common.message.InternationalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final InternationalizationService i18nService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse("Invalid value!"),
                        (existing, replacement) -> existing
                ));
        ErrorResponse error =
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "", validationErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BaseConflictException.class)
    public ResponseEntity<ErrorResponse> handleBaseConflictException(BaseConflictException exception) {
        String message = i18nService.getMessage(exception.getMessage(), exception.getArgs());
        ErrorResponse error =
                new ErrorResponse(HttpStatus.CONFLICT.value(), message, null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
