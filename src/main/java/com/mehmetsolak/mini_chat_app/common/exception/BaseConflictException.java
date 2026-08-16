package com.mehmetsolak.mini_chat_app.common.exception;

import lombok.Getter;

@Getter
public class BaseConflictException extends BaseException{
    private final Object[] args;

    public BaseConflictException(String messageKey, Object... args){
        super(messageKey);
        this.args = args;
    }
}
