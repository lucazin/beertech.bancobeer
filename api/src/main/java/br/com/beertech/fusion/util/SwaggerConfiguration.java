package br.com.beertech.fusion.util;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/beercoin/.*";

    @Bean
    public Docket swaggerSpringfoxDocket() {

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("BeerTech Developer Program");
        apiInfoBuilder.description("Legacy POC / Grupo Fusion - Controle Bancário");
        apiInfoBuilder.version("V1.0.0");
        apiInfoBuilder.contact(new Contact("Grupo Fusion", ":)", "n/a"));
        apiInfoBuilder.license("GNU General Public License");
        apiInfoBuilder.licenseUrl("https://www.gnu.org/licenses/gpl-3.0.en.html");
        apiInfoBuilder.termsOfServiceUrl("https://github.com/lucazin");

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .apiInfo(ApiInfo.DEFAULT)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .useDefaultResponseMessages(false).apiInfo(apiInfo());

        docket = docket.select()
                .paths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();

        return docket;
    }

    private ApiInfo apiInfo() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("BeerTech Developer Program");
        apiInfoBuilder.description("Legacy POC / Grupo Fusion - Controle Bancário");
        apiInfoBuilder.version("V1.0.0");
        apiInfoBuilder.contact(new Contact("Grupo Fusion", ":)", "n/a"));
        apiInfoBuilder.license("GNU General Public License");
        apiInfoBuilder.licenseUrl("https://www.gnu.org/licenses/gpl-3.0.en.html");
        apiInfoBuilder.termsOfServiceUrl("https://github.com/lucazin");
        return apiInfoBuilder.build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }
}