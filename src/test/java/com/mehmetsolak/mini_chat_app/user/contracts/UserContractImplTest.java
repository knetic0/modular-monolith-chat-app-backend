package com.mehmetsolak.mini_chat_app.user.contracts;

import com.mehmetsolak.mini_chat_app.common.event.UserCreatedEvent;
import com.mehmetsolak.mini_chat_app.user.api.contracts.impl.UserContractImpl;
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
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserContractImpl#create Unit Tests")
public class UserContractImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private UserContractImpl userContract;

    @Test
    @DisplayName("It must save the user and publish UserCreatedEvent when user does not already exist")
    public void shouldCreateUser_whenUserDoesNotExist_shouldSaveAndPublishEvent() {
        String username = "username";
        String email = "email";
        String firstName = "firstName";
        String lastName = "lastName";
        String hashedPassword = "hashedPassword";

        when(userRepository.existsByUsernameOrEmail(username, email)).thenReturn(false);

        userContract.create(username, email, firstName, lastName, hashedPassword);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(username, savedUser.getUsername());
        assertEquals(email, savedUser.getEmail());
        assertEquals(firstName, savedUser.getFirstName());
        assertEquals(lastName, savedUser.getLastName());
        assertEquals(hashedPassword, savedUser.getPassword());

        ArgumentCaptor<UserCreatedEvent> eventCaptor = ArgumentCaptor.forClass(UserCreatedEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());

        UserCreatedEvent event = eventCaptor.getValue();
        assertEquals(firstName, event.firstName());
        assertEquals(lastName, event.lastName());
        assertEquals(email, event.email());
    }

    @Test
    @DisplayName("It must throw UserAlreadyExistsException and not save or publish event when user already exists")
    public void shouldThrowException_whenUserAlreadyExists() {
        String username = "username";
        String email = "email";

        when(userRepository.existsByUsernameOrEmail(username, email)).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class,
                () -> userContract.create(username, email, "firstName", "lastName", "hashedPassword"));

        verify(userRepository, never()).save(any());
        verify(eventPublisher, never()).publishEvent(any());
    }
}
