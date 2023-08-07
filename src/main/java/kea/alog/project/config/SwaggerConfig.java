package kea.alog.project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
            .title("A-log Project API")
            .description("A-log Project 서비스 Rest API")
            .version("1.0.0");

        return new OpenAPI()
            .components(new Components())
            .info(info);
    }

    @Bean
    public GroupedOpenApi postGroup() {
        return GroupedOpenApi.builder()
                             .group("v1")
                             .packagesToScan("kea.alog.project")
                             .build();
    }
}