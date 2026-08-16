package com.mehmetsolak.mini_chat_app.communication.api.dto.response;

public record DirectMessageResponse(
        String sender,
        String content
) { }
