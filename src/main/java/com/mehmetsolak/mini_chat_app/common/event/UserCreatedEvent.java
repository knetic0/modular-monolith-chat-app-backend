package com.mehmetsolak.mini_chat_app.common.event;

public record UserCreatedEvent(
        String firstName,
        String lastName,
        String email
) { }
