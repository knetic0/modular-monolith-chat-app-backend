package com.mehmetsolak.mini_chat_app.communication.api.dto.request;

public record DirectMessageRequest(
        String receiver,
        String content
) { }
