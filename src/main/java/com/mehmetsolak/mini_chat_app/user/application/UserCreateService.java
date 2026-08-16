package com.mehmetsolak.mini_chat_app.user.application;

import com.mehmetsolak.mini_chat_app.common.event.UserCreateEvent;
import com.mehmetsolak.mini_chat_app.user.domain.exceptions.UserAlreadyExistsException;
import com.mehmetsolak.mini_chat_app.user.domain.models.User;
import com.mehmetsolak.mini_chat_app.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreateService {

    private final UserRepository userRepository;

    @EventListener
    public void handleUserCreatedEvent(UserCreateEvent event) {
        if(userRepository.existsByUsernameOrEmail(event.username(), event.email()))
            throw new UserAlreadyExistsException();
        User user = User.builder()
                .username(event.username())
                .email(event.email())
                .firstName(event.firstName())
                .lastName(event.lastName())
                .password(event.hashedPassword())
                .build();
        userRepository.save(user);
    }
}
