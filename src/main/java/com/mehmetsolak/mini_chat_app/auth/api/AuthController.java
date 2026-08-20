package com.mehmetsolak.mini_chat_app.auth.api;

import com.mehmetsolak.mini_chat_app.auth.api.dto.request.LoginRequest;
import com.mehmetsolak.mini_chat_app.auth.api.dto.request.RegisterRequest;
import com.mehmetsolak.mini_chat_app.auth.application.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SecurityContextRepository securityContextRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;
    private final AuthService authService;

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken csrfToken) {
        return csrfToken;
    }

    @PostMapping("/login")
    public void authenticate(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                .unauthenticated(loginRequest.username(), loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        authService.register(
                request.username(),
                request.email(),
                request.firstName(),
                request.lastName(),
                request.password());
        return ResponseEntity.status(201).build();
    }
}
