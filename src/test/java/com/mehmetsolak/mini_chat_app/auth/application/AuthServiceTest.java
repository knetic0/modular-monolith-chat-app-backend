package com.mehmetsolak.mini_chat_app.auth.application;

import com.mehmetsolak.mini_chat_app.common.event.UserCreateEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Unit Tests")
public class AuthServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("It must be register a user if email or username not exists")
    public void shouldRegisterUser_whenUserDoesNotExist_shouldCaptorEvent() {
        String username = "username";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email";

        String hashedPassword = "hashedPassword";

        when(passwordEncoder.encode(password)).thenReturn(hashedPassword);

        authService.register(username, email, firstName, lastName, password);

        ArgumentCaptor<UserCreateEvent> captor = ArgumentCaptor.forClass(UserCreateEvent.class);

        verify(passwordEncoder).encode(password);
        verify(eventPublisher).publishEvent(captor.capture());

        UserCreateEvent event = captor.getValue();

        assertEquals(username, event.username());
        assertEquals(hashedPassword, event.hashedPassword());
        assertEquals(firstName, event.firstName());
        assertEquals(lastName, event.lastName());
        assertEquals(email, event.email());
    }
}
