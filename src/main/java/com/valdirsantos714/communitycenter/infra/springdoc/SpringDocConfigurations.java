package com.valdirsantos714.communitycenter.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Community Center API")
                        .description("Community Center é uma API que oferece funcionalidades para cadastrar, atualizar, deletar e buscar centros comunitários, além de oferecer formas de pagamento.")
                        .contact(new Contact()
                                .name("Valdir Santis")
                                .url("https://github.com/valdirsantos714")));
                
    }
}