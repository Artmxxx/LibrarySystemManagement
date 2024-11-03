package com.example.OODevProject.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("OODevProject API")
                        .description("API documentation for OO Systems Development project")
                        .version("1.0")
                        .contact(new Contact().name("Manal Gharrabou/Quentin Lauriot").email("manal.gharrabou@efrei.net"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("OODevProject Project Documentation")
                        .url("https://github.com/your-repo-url"));
    }
}
