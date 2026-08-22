package com.mehmetsolak.mini_chat_app.blob.application.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mehmetsolak.mini_chat_app.blob.api.contracts.dto.BlobContractResponse;
import com.mehmetsolak.mini_chat_app.blob.application.StorageService;
import com.mehmetsolak.mini_chat_app.blob.exceptions.FileDeleteException;
import com.mehmetsolak.mini_chat_app.blob.exceptions.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryStorageService implements StorageService {

    private final Cloudinary cloudinary;

    @Override
    public BlobContractResponse upload(MultipartFile file, String folderName) {
        try {
            Map<?, ?> options = ObjectUtils.asMap(
                    "folder", folderName,
                    "resource_type", "auto"
            );
            Map<?, ?> response = cloudinary.uploader().upload(file.getBytes(), options);
            return BlobContractResponse
                    .builder()
                    .url(String.valueOf(response.get("secure_url")))
                    .publicId(String.valueOf(response.get("public_id")))
                    .build();
        } catch (IOException ex) {
            throw new FileUploadException("An error occurred while uploading file!", ex);
        }
    }

    @Override
    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new FileDeleteException("An error occurred while deleting file!", e);
        }
    }
}
