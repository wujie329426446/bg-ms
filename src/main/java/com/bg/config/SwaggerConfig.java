package com.bg.config;

import com.bg.commons.constant.LoginConstant;
import com.bg.config.properties.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SwaggerConfig {

  private final SwaggerProperties swaggerProperties;

  @Bean
  public OpenAPI customOpenAPI() {
    Contact contact = new Contact().name(swaggerProperties.getContactName()).email(swaggerProperties.getContactEmail()).url(swaggerProperties.getContactUrl());
    OpenAPI openAPI = new OpenAPI().info(
        new Info()
            .title(swaggerProperties.getTitle())
            .version(swaggerProperties.getVersion())
            .description(swaggerProperties.getDescription())
            .contact(contact)
    );
    openAPI.setComponents(new Components().addSecuritySchemes(LoginConstant.BG_HEADER, new SecurityScheme().type(Type.HTTP).in(In.HEADER)));
    openAPI.addSecurityItem(new SecurityRequirement().addList(LoginConstant.BG_HEADER));
    return openAPI;
  }

}
