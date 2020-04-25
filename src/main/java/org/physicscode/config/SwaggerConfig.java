package org.physicscode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.util.List;

import static springfox.documentation.builders.PathSelectors.any;

@Configuration
@EnableSwagger2WebFlux
@Profile("!pro, !prod") //Just a friendly remainder that this should not be present in PRO env.
public class SwaggerConfig {

    @Bean
    public Docket publicApiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .securitySchemes(List.of(
                        new ApiKey("JWT token", "Authentication", "header")))
                .groupName("e-boost-api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.physicscode.controller"))
                .paths(any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("Cart API")
                        .version("v1")
                        .description("Cart API V1 Public spec")
                        .build()
                );

    }
}
