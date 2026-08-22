package com.mehmetsolak.mini_chat_app.blob.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class BlobStorageRestConfiguration {

    @Bean
    public RestClient cloudinaryRestClient(
            @Value("${cloudinary.baseUrl}") String baseUrl,
            @Value("${cloudinary.productEnv}") String env
    ) {
        return RestClient.builder()
                .baseUrl(baseUrl + "/" + env)
                .build();
    }
}
