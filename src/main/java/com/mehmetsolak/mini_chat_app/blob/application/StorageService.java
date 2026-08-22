package com.mehmetsolak.mini_chat_app.blob.application;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String upload(MultipartFile file, String folderName);
}
