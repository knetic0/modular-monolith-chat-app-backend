package com.mehmetsolak.mini_chat_app.blob.api.contracts.dto;

import lombok.Builder;

@Builder
public record BlobContractResponse(String url, String publicId) { }
