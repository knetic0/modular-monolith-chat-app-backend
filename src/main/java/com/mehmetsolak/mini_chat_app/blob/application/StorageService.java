package com.mehmetsolak.mini_chat_app.blob.application;

import com.mehmetsolak.mini_chat_app.blob.api.contracts.dto.BlobContractResponse;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    BlobContractResponse upload(MultipartFile file, String folderName);
    void delete(String publicId);
}
