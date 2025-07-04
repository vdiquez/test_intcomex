package com.intcomex.productapi.infrastructure.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Intcomex Product API")
                .version("1.0")
                .description("Documentación de la API de Productos y Categorías para el reto técnico de Líder Técnico"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("api")
            .pathsToMatch("/api/**", "/auth/**")
            .build();
    }
}
