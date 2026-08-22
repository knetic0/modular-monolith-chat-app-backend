package com.mehmetsolak.mini_chat_app.user.domain.exceptions;

import com.mehmetsolak.mini_chat_app.common.exception.BaseNotFoundException;

public class UserNotFoundException extends BaseNotFoundException {
    public UserNotFoundException() {
        super("user.notFound.exception");
    }
}
