package dev4._team.cafemenu._team.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Operation;
import java.util.ArrayList;

@OpenAPIDefinition(
        info = @Info(
                title = "Team10 API 명세서",
                version = "v1.0.0",
                description = "Team10의 REST API 명세입니다.",
                contact = @io.swagger.v3.oas.annotations.info.Contact(
                        name = "Team10 Support",
                        email = "support@team10.com"
                )
        ),
        security = @SecurityRequirement(name = "bearerAuth") // 모든 API에 기본적으로 Authorization 헤더 추가
)
@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI()
                .components(new Components())
                .info(apiInfo());

        // 사용자 정의 경로 설정 적용
        openAPI.paths(filterPaths());

        return openAPI;
    }


    private Paths filterPaths() {
        Paths paths = new Paths();

        // /user/login 경로의 POST 요청에 대해 인증을 제거
        PathItem loginPathItem = new PathItem()
                .post(new Operation().security(new ArrayList<>()));
        paths.addPathItem("/api/user/login", loginPathItem);

        // /user/signup 경로의 POST 요청에 대해 인증을 제거
        PathItem signupPathItem = new PathItem()
                .post(new Operation().security(new ArrayList<>()));
        paths.addPathItem("/api/user/signup", signupPathItem);

        return paths;
    }


    private io.swagger.v3.oas.models.info.Info apiInfo() {
        return new io.swagger.v3.oas.models.info.Info()
                .title("Spring Boot REST API Specifications")
                .description("API 명세를 확인하세요.")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Team10 Support")
                        .email("support@team10.com")
                        .url("https://team10.com")
                )
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html")
                );
    }
}
