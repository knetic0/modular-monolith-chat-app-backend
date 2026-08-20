package com.mehmetsolak.mini_chat_app.auth.security;

import com.mehmetsolak.mini_chat_app.user.api.contracts.UserContract;
import com.mehmetsolak.mini_chat_app.user.api.contracts.dto.UserAuthenticationInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomUserDetailsService Unit Tests")
public class CustomUserDetailsServiceTest {

    @Mock
    private UserContract userContract;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("It must be return user information with credentials if user exists")
    public void loadUser_whenUserExists_shouldReturnUserWithCredentials() {
        UUID userId = UUID.randomUUID();
        UserAuthenticationInfo user =
                new UserAuthenticationInfo(userId, "mehmet.solak", "mehmet@example.com", "hashed-password");

        when(userContract.getUserAuthenticationInfo("mehmet.solak"))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("mehmet.solak");

        assertEquals("mehmet.solak", userDetails.getUsername());
        verify(userContract).getUserAuthenticationInfo("mehmet.solak");
    }

    @Test
    @DisplayName("It must be throw invalid credentials exception if user does not exists")
    public void throwInvalidCredentialsException_whenUserDoesNotExist_shouldThrowException() {
        when(userContract.getUserAuthenticationInfo("mehmet.solak"))
            .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("mehmet.solak"));

        verify(userContract).getUserAuthenticationInfo("mehmet.solak");
    }
}
