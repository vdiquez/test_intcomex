package com.intcomex.productapi.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

@Configuration
@OpenAPIDefinition (
    info = @Info(
        title = "Intcomex Product API",
        version = "1.0",
        description = "Documentación de la API de Productos y Categorías para el reto técnico de Líder Técnico"
    )
)
public class OpenApiConfig {

}
