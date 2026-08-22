package com.mehmetsolak.mini_chat_app.blob.api.contracts;

import com.mehmetsolak.mini_chat_app.blob.api.contracts.dto.BlobContractResponse;
import org.springframework.web.multipart.MultipartFile;

public interface BlobContract {
    BlobContractResponse uploadAvatar(MultipartFile file);
    void delete(String publicId);
}
