package com.keyword.keywordsearch.common.config.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(servers = [Server(url = "/", description = "Default Server URL")])
@Configuration
class SwaggerConfig {

    @Bean
    fun v1API(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("blog")
            .pathsToMatch("/api/blog/**")
            .build()
    }

    @Bean
    fun customOpenAPI(): OpenAPI {
        val contact = Contact()
        contact.url = "http://localhost:8081/swagger-ui/index.html"
        return OpenAPI()
            .components(Components())
            .info(
                Info().title("Blog Search Service HTTP API")
                    .description("API 명세 문서는 아래 URL 클릭")
                    .contact(contact)
                    .version("1.0.0")
            )
    }
}