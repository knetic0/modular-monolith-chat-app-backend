package com.mehmetsolak.mini_chat_app.auth.application;

import com.mehmetsolak.mini_chat_app.user.api.contracts.UserContract;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserContract userContract;

    @Transactional
    public void register(String username, String email, String firstName, String lastName, String password) {
        String hashed = passwordEncoder.encode(password);
        userContract.create(username, email, firstName, lastName, hashed);
    }
}
