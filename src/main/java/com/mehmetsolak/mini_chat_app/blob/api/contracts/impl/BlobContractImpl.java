package com.mehmetsolak.mini_chat_app.blob.api.contracts.impl;

import com.mehmetsolak.mini_chat_app.blob.api.contracts.BlobContract;
import com.mehmetsolak.mini_chat_app.blob.api.contracts.dto.BlobContractResponse;
import com.mehmetsolak.mini_chat_app.blob.application.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BlobContractImpl implements BlobContract {

    private final StorageService storageService;

    @Override
    public BlobContractResponse uploadAvatar(MultipartFile file) {
        return storageService.upload(file, "avatars");
    }

    @Override
    public void delete(String publicId) {
        storageService.delete(publicId);
    }
}
