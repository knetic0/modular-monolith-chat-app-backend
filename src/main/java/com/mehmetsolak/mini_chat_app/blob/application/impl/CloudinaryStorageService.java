package com.mehmetsolak.mini_chat_app.blob.application.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mehmetsolak.mini_chat_app.blob.application.StorageService;
import com.mehmetsolak.mini_chat_app.blob.exceptions.FileUploadException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("cloudinary")
@RequiredArgsConstructor
public class CloudinaryStorageService implements StorageService {

    private final RestClient cloudinaryRestClient;

    @Value("${cloudinary.apiKey}")
    private String apiKey;

    @Value("${cloudinary.apiSecret}")
    private String apiSecret;

    @Getter
    @Setter
    private static class UploadResponse {

        @JsonProperty("public_id")
        private String publicId;

        @JsonProperty("secure_url")
        private String secureUrl;

        private String url;
        private Long bytes;
        private String format;
    }

    @Override
    public String upload(MultipartFile file, String folderName) {
        try {
            String timestamp = currentTimestamp();
            Map<String, String> params =
                    new TreeMap<>(Map.of("timestamp", timestamp, "folder", folderName));

            String signature = generateSignature(params);

            UploadResponse response = cloudinaryRestClient
                    .post()
                    .uri("/upload")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(createMultipartBody(file, timestamp, signature, folderName))
                    .retrieve()
                    .body(UploadResponse.class);

            if(Objects.isNull(response))
                throw new FileUploadException("The response is null!");

            return response.getSecureUrl();
        } catch (IOException e) {
            throw new FileUploadException("Error occurred while file processing!", e);
        }
    }

    private MultiValueMap<String, Object> createMultipartBody(
            MultipartFile file,
            String timestamp,
            String signature,
            String folderName
    ) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        String originalFilename = file.getOriginalFilename();
        String safeFilename = (originalFilename == null || originalFilename.isBlank())
                ? UUID.randomUUID().toString()
                : originalFilename;

        ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return safeFilename;
            }
        };

        body.add("file", fileResource);
        body.add("api_key", apiKey);
        body.add("timestamp", timestamp);
        body.add("signature", signature);
        body.add("folder", folderName);

        return body;
    }

    private String generateSignature(Map<String, String> params) {
        try {
            TreeMap<String, String> sorted = new TreeMap<>(params);

            String toSign = sorted.entrySet().stream()
                    .map(e -> e.getKey() + "=" + e.getValue())
                    .collect(Collectors.joining("&"));

            toSign += apiSecret;

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(toSign.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : digest) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (Exception e) {
            throw new FileUploadException("Cloudinary signature generation failed", e);
        }
    }

    private String currentTimestamp() {
        return String.valueOf(Instant.now().getEpochSecond());
    }
}
