package com.mehmetsolak.mini_chat_app.user.api;

import com.mehmetsolak.mini_chat_app.user.api.dto.request.UploadAvatarRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @PostMapping("/avatar/upload")
    public void uploadAvatar(@Valid @ModelAttribute UploadAvatarRequest request) {

    }

    @GetMapping("/avatar")
    public ResponseEntity<?> avatar() {
        return ResponseEntity.ok().build();
    }
}
