package com.mehmetsolak.mini_chat_app.blob.api.contracts;

import org.springframework.web.multipart.MultipartFile;

public interface BlobContract {
    String uploadAvatar(MultipartFile file);
}
