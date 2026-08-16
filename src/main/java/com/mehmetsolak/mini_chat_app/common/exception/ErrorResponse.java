package com.mehmetsolak.mini_chat_app.common.exception;

import java.util.Map;

public record ErrorResponse(
        Integer statusCode, String message, Map<String, String> validationErrors
) { }
