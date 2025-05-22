package com.bit.microservices.mitra.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.HeaderWriterServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.header.ClearSiteDataServerHttpHeadersWriter;
import org.springframework.security.web.server.header.ClearSiteDataServerHttpHeadersWriter.Directive;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@EnableConfigurationProperties({OauthProperties.class})
public class WebSecurityConfig{

	private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";

	@Setter(onMethod_ = @Autowired)
	private ObjectMapper mapper;

	@Setter(onMethod_ = @Autowired)
	private OauthProperties oauthProperties;

	@Bean
	@Profile("test")
	SecurityWebFilterChain configureTest(ServerHttpSecurity http) {
		http.cors(ServerHttpSecurity.CorsSpec::disable);
		http.csrf(ServerHttpSecurity.CsrfSpec::disable);
		http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
		http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
		http.requestCache(cache -> new NullRequestCache());

		http.authorizeExchange((authorize) -> authorize
				.anyExchange()
				.permitAll()
		);

		return http.build();
	}
	
	@Bean
	@Profile("!test")
	SecurityWebFilterChain configure(ServerHttpSecurity http) {


		http.cors(ServerHttpSecurity.CorsSpec::disable);
		http.csrf(ServerHttpSecurity.CsrfSpec::disable);
		http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
		http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
		http.requestCache(cache -> new NullRequestCache());

		http.authorizeExchange(authorize -> authorize
				.pathMatchers(HttpMethod.OPTIONS).permitAll()
				.pathMatchers("*/actuator/**", "/actuator/**").permitAll()
                .pathMatchers("*/v3/api-docs/**").permitAll()
                .pathMatchers("*/swagger-ui/*").permitAll()
               	.pathMatchers(oauthProperties.getWhitelistPath()).permitAll()
				.anyExchange()
				.authenticated()
		);

		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtDecoder(reactiveJwtDecoder())));
		
		http.logout(logout -> logout.logoutHandler(logoutHandler()));
		http.securityContextRepository(new WebSessionServerSecurityContextRepository());

		return http.build();
	}
	
	private ServerLogoutHandler logoutHandler() {
		ServerLogoutHandler securityContext = new SecurityContextServerLogoutHandler();
		ClearSiteDataServerHttpHeadersWriter writer = new ClearSiteDataServerHttpHeadersWriter(Directive.CACHE,
				Directive.COOKIES);
		
		ServerLogoutHandler clearSiteData = new HeaderWriterServerLogoutHandler(writer);
		
		return new DelegatingServerLogoutHandler(securityContext, clearSiteData);
	}

	
	@Bean
 	CorsConfigurationSource corsConfiguration() {

 		CorsConfiguration corsConfiguration = new CorsConfiguration();
 		corsConfiguration.applyPermitDefaultValues();
 		corsConfiguration.setAllowedMethods(Collections.singletonList(ALLOWED_METHODS));
 		corsConfiguration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
 		corsConfiguration.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));

 		UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
 		corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

 		return corsConfigurationSource;
 	}

	
	@Bean
	@Primary
    JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(oauthProperties.getResourceServer().getJwt().getIssuerUri());
    }
	@Bean
	ReactiveJwtDecoder reactiveJwtDecoder() {
		return ReactiveJwtDecoders.fromIssuerLocation(oauthProperties.getResourceServer().getJwt().getIssuerUri());
	}

	@Bean
    AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher appEventPublisher) {
      return new DefaultAuthenticationEventPublisher(appEventPublisher);
    }

	@Bean
    WebSessionIdResolver webSessionIdResolver() {
        HeaderWebSessionIdResolver headerWebSessionIdResolver = new HeaderWebSessionIdResolver();
        headerWebSessionIdResolver.setHeaderName("X-Auth-Token");
        return headerWebSessionIdResolver;
    }
	
}
