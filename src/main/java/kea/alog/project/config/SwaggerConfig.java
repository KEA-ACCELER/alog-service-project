package kea.alog.project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import java.util.Arrays;
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

        SecurityScheme securityScheme = new SecurityScheme()
            .type(Type.HTTP).scheme("bearer").bearerFormat("JWT")
            .in(In.HEADER).name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer");

        return new OpenAPI()
            .components(new Components().addSecuritySchemes("Bearer", securityScheme))
            .security(Arrays.asList(securityRequirement))
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