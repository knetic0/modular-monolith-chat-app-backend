package com.mehmetsolak.mini_chat_app.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final String messageKey;

    public BaseException(String messageKey) {
        super(messageKey);
        this.messageKey = messageKey;
    }
}
