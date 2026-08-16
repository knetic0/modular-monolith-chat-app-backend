package com.mehmetsolak.mini_chat_app.user.api.contracts.impl;

import com.mehmetsolak.mini_chat_app.user.api.contracts.UserContract;
import com.mehmetsolak.mini_chat_app.user.api.contracts.dto.UserAuthenticationInfo;
import com.mehmetsolak.mini_chat_app.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserContractImpl implements UserContract {

    private final UserRepository userRepository;

    @Override
    public Optional<UserAuthenticationInfo> getUserAuthenticationInfo(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new UserAuthenticationInfo(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword()
                ));
    }
}
