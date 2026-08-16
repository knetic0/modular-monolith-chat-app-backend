package com.mehmetsolak.mini_chat_app.user.api.contracts;

import com.mehmetsolak.mini_chat_app.user.api.contracts.dto.UserAuthenticationInfo;

import java.util.Optional;

public interface UserContract {
    Optional<UserAuthenticationInfo> getUserAuthenticationInfo(String username);
}
