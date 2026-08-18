package com.mehmetsolak.mini_chat_app.user.application;

import com.mehmetsolak.mini_chat_app.common.event.UserCreateEvent;
import com.mehmetsolak.mini_chat_app.user.domain.exceptions.UserAlreadyExistsException;
import com.mehmetsolak.mini_chat_app.user.domain.models.User;
import com.mehmetsolak.mini_chat_app.user.infrastructure.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserCreateService Unit Tests")
public class UserCreateServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserCreateService userCreateService;

    @Test
    @DisplayName("It must be create user if user does not exists")
    public void shouldCreateUser_whenUserNotExists_shouldCaptorUser() {
        UserCreateEvent event = new UserCreateEvent(
                "mehmet",
                "Mehmet",
                "Solak",
                "mehmet@test.com",
                "hashed-password"
        );

        when(userRepository.existsByUsernameOrEmail(
                event.username(),
                event.email()
        )).thenReturn(false);

        userCreateService.handleUserCreateEvent(event);

        ArgumentCaptor<User> captor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(captor.capture());

        User savedUser = captor.getValue();

        assertEquals("mehmet", savedUser.getUsername());
        assertEquals("mehmet@test.com", savedUser.getEmail());
        assertEquals("Mehmet", savedUser.getFirstName());
        assertEquals("Solak", savedUser.getLastName());
        assertEquals("hashed-password", savedUser.getPassword());
    }

    @Test
    @DisplayName("")
    public void shouldNotCreateUser_whenUserExists_shouldThrowUserExistsException() {
        UserCreateEvent event = new UserCreateEvent(
                "mehmet",
                "Mehmet",
                "Solak",
                "mehmet@test.com",
                "hashed-password"
        );

        when(userRepository.existsByUsernameOrEmail(
                event.username(),
                event.email()
        )).thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> userCreateService.handleUserCreateEvent(event)
        );

        verify(userRepository)
                .existsByUsernameOrEmail(event.username(), event.email());

        verify(userRepository, never())
                .save(any(User.class));
    }
}
