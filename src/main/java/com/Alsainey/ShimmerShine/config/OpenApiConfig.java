package com.Alsainey.ShimmerShine.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ShimmerShine Backend API")
                        .version("1.0")
                        .description("Backend Structure for ShimmerShine")
                        .contact(new Contact()
                                .name("Alsainey Barry")
                                .email("alsainey7@gmail.com")
                        )
                );
    }
}
