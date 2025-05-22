package com.bit.microservices.mitra.configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Slf4j
@Configuration
public class OpenApiConfig {


	private String appVersion = System.getenv("APP_VERSION");

//	@Bean
//	GroupedOpenApi v1Level0OpenApi() {
//	    return GroupedOpenApi.builder()
//	    		.group("v1 level 0")
//				.pathsToExclude("/**/v1/1/**")
//				.pathsToMatch("/**/v1/0/**")
//				.addOpenApiCustomizer(internalApiCustomizer())
//				.build();
//	}

	@Bean
	GroupedOpenApi v1Level0OpenApi() {

	    return GroupedOpenApi.builder()
	    		.group("v1 level 0")
				.pathsToMatch("/**/v1/0/**")
				.addOpenApiCustomizer(internalApiCustomizer())
				.build();
	}

	@Bean
	OpenApiCustomizer internalApiCustomizer() {
		return openApi -> {
			openApi.addSecurityItem(new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")));
			Info info = openApi.getInfo();
			info.setVersion(appVersion);
			openApi.setInfo(info);
			openApi.getComponents().addSecuritySchemes("bearer-jwt",
					new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")
					.in(SecurityScheme.In.HEADER)
					.name("Authorization"));
		};
	}
}
