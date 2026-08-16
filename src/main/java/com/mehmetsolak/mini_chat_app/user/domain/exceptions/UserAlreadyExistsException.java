package com.mehmetsolak.mini_chat_app.user.domain.exceptions;

import com.mehmetsolak.mini_chat_app.common.exception.BaseConflictException;

public class UserAlreadyExistsException extends BaseConflictException {
    public UserAlreadyExistsException() {
        super("user.create.alreadyExists");
    }
}
