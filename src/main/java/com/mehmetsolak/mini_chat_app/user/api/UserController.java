package com.mehmetsolak.mini_chat_app.user.api;

import com.mehmetsolak.mini_chat_app.auth.security.CustomUserDetails;
import com.mehmetsolak.mini_chat_app.user.api.dto.request.UploadAvatarRequest;
import com.mehmetsolak.mini_chat_app.user.application.UserAvatarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserAvatarService userAvatarService;

    @PostMapping("/avatar/upload")
    public void uploadAvatar(
            @Valid @ModelAttribute UploadAvatarRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        userAvatarService.upload(request.avatar(), userDetails.getId());
    }

    @GetMapping("/avatar")
    public ResponseEntity<?> avatar() {
        return ResponseEntity.ok().build();
    }
}
