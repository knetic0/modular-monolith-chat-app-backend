package com.mehmetsolak.mini_chat_app.user.api.contracts.dto;

import java.util.UUID;

public record UserAuthenticationInfo(UUID id, String username, String email, String password) { }
