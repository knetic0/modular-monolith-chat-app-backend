package com.mehmetsolak.mini_chat_app.user.api.dto.request;

import com.mehmetsolak.mini_chat_app.user.api.validators.MaxFileSize;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

public record UploadAvatarRequest(
    @NonNull @MaxFileSize(value = 1_048_576, message = "user.uploadAvatar.fileSizeExceeded")
    MultipartFile avatar
) { }
