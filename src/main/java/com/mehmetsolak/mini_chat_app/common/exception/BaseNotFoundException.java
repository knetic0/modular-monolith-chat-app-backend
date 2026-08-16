package com.mehmetsolak.mini_chat_app.common.exception;

import lombok.Getter;

@Getter
public class BaseNotFoundException extends BaseException {
    private final Object[] args;

    public BaseNotFoundException(String messageKey, Object[] args) {
        super(messageKey);
        this.args = args;
    }
}
