package com.mehmetsolak.mini_chat_app.user.api.dto.request;

import com.mehmetsolak.mini_chat_app.user.api.validators.MaxFileSize;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UploadAvatarRequest(
    @NotNull @MaxFileSize(value = 1_048_576, message = "user.uploadAvatar.fileSizeExceeded")
    MultipartFile avatar
) { }
