package com.bbg.box.config;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(name = "token",type = SecuritySchemeType.APIKEY,paramName = "token", in = SecuritySchemeIn.HEADER)
class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().security(List.of(new SecurityRequirement().addList("token")));
    }

}
