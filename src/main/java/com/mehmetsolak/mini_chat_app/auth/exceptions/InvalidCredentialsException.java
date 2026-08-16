package com.mehmetsolak.mini_chat_app.auth.exceptions;

import com.mehmetsolak.mini_chat_app.common.exception.BaseException;

public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException() {
        super("auth.login.invalidCredentials");
    }
}
