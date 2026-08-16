package com.mehmetsolak.mini_chat_app.auth.application;

import com.mehmetsolak.mini_chat_app.common.event.UserCreateEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void register(String username, String email, String firstName, String lastName, String password) {
        String hashed = passwordEncoder.encode(password);
        UserCreateEvent event = new UserCreateEvent(username, firstName, lastName, email, hashed);
        eventPublisher.publishEvent(event);
    }
}
