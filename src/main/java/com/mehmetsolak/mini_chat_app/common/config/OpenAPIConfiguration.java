package com.mehmetsolak.mini_chat_app.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    private static final String SESSION_AUTH = "sessionAuth";

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Mini Chat App")
                .version("1.0")
                .description("Mini Chat App - Version 1.0");

        SecurityScheme sessionSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("JSESSIONID")
                .description("Session authentication via JSESSIONID cookie");

        SecurityRequirement securityRequirement =
                new SecurityRequirement()
                        .addList(SESSION_AUTH);

        Components components = new Components()
                .addSecuritySchemes(
                        SESSION_AUTH,
                        sessionSecurityScheme
                );

        return new OpenAPI()
                .info(info)
                .components(components)
                .addSecurityItem(securityRequirement);
    }
}
