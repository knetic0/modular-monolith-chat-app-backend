package com.mehmetsolak.mini_chat_app.auth.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank(message = "{auth.register.username.required}")
        String username,

        @NotBlank(message = "{auth.register.firstName.required}")
        String firstName,

        @NotBlank(message = "{auth.register.lastName.required}")
        String lastName,

        @NotBlank(message = "{auth.register.password.required}")
        String password,

        @NotBlank(message = "{auth.register.email.required}")
        @Email(message = "{auth.register.email.invalid}")
        String email
) { }
