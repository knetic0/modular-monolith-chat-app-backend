package com.mehmetsolak.mini_chat_app.auth.application;

import com.mehmetsolak.mini_chat_app.user.api.contracts.UserContract;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Unit Tests")
public class AuthServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserContract userContract;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("It must hash the password and delegate registration to UserContract")
    public void shouldRegisterUser_whenCalled_shouldHashPasswordAndDelegateToUserContract() {
        String username = "username";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email";
        String hashedPassword = "hashedPassword";

        when(passwordEncoder.encode(password)).thenReturn(hashedPassword);

        authService.register(username, email, firstName, lastName, password);

        verify(passwordEncoder).encode(password);
        verify(userContract).create(username, email, firstName, lastName, hashedPassword);
    }
}
