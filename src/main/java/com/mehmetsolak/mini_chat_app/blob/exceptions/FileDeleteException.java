package com.mehmetsolak.mini_chat_app.blob.exceptions;

public class FileDeleteException extends RuntimeException {
    public FileDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileDeleteException(String message) {
        super(message);
    }
}
