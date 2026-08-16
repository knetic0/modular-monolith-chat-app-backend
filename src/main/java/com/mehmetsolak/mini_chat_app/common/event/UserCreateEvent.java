package com.mehmetsolak.mini_chat_app.common.event;

public record UserCreateEvent(
        String username,
        String firstName,
        String lastName,
        String email,
        String hashedPassword
) { }
