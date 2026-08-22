package com.mehmetsolak.mini_chat_app.user.api.contracts.impl;

import com.mehmetsolak.mini_chat_app.common.event.UserCreatedEvent;
import com.mehmetsolak.mini_chat_app.user.api.contracts.UserContract;
import com.mehmetsolak.mini_chat_app.user.api.contracts.dto.UserAuthenticationInfo;
import com.mehmetsolak.mini_chat_app.user.domain.exceptions.UserAlreadyExistsException;
import com.mehmetsolak.mini_chat_app.user.domain.models.User;
import com.mehmetsolak.mini_chat_app.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserContractImpl implements UserContract {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

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

    @Override
    public void create(String username, String email, String firstName, String lastName, String hashedPassword) {
        if(userRepository.existsByUsernameOrEmail(username, email))
            throw new UserAlreadyExistsException();
        User user = User.builder()
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(hashedPassword)
                .build();
        userRepository.save(user);
        UserCreatedEvent event = new UserCreatedEvent(firstName, lastName, email);
        eventPublisher.publishEvent(event);
    }
}
