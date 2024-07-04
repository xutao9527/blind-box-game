package com.bbg.box.config;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(name = "token", type = SecuritySchemeType.APIKEY, paramName = "token", in = SecuritySchemeIn.HEADER, description = "token")
@SecurityScheme(name = "t_code", type = SecuritySchemeType.APIKEY, paramName = "t_code", in = SecuritySchemeIn.HEADER, description = "租户编码")
class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.addSecurityItem(new SecurityRequirement().addList("token").addList("t_code"));
        return openAPI;
    }

}
