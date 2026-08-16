package com.mehmetsolak.mini_chat_app.auth.security;

import com.mehmetsolak.mini_chat_app.auth.exceptions.InvalidCredentialsException;
import com.mehmetsolak.mini_chat_app.user.api.contracts.UserContract;
import com.mehmetsolak.mini_chat_app.user.api.contracts.dto.UserAuthenticationInfo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserContract userContract;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) {
        UserAuthenticationInfo user = userContract
                .getUserAuthenticationInfo(username)
                .orElseThrow(InvalidCredentialsException::new);
        return new CustomUserDetails(user.id(), user.username(), user.email(), user.password());
    }
}
