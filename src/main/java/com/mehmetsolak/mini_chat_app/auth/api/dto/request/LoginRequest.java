package com.mehmetsolak.mini_chat_app.auth.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotBlank(message = "{auth.login.username.required}") String username,
        @NotBlank(message = "{auth.login.password.required}") String password
) { }
