package com.mehmetsolak.mini_chat_app.blob.exceptions;

import com.mehmetsolak.mini_chat_app.common.exception.ErrorResponse;
import com.mehmetsolak.mini_chat_app.common.message.InternationalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class BlobExceptionHandler {

    private final InternationalizationService i18nService;

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handleFileUploadException() {
        String message = i18nService.getMessage("blob.fileUpload.exception");
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message,
                null
        );
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(FileDeleteException.class)
    public ResponseEntity<ErrorResponse> handleFileDeleteException() {
        String message = i18nService.getMessage("blob.fileDelete.exception");
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message,
                null
        );
        return ResponseEntity.internalServerError().body(response);
    }
}
