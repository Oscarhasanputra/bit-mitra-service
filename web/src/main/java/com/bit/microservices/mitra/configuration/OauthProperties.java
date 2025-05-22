package com.bit.microservices.mitra.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("spring.security.oauth2")
public class OauthProperties {
    
    private Token token;
    private Client client;
    private String[] whitelistPath;
    private ResourceServer resourceServer;

    @Data
    public static class Token {
        private int validitySeconds;
        private int refreshValiditySeconds;
    }

    @Data
    public static class Client {
        
        private Registration registration;
        private Provider provider;

    }
    
    @Data
    public static class Registration {
    	private RegistrationDetail oidc;
    }
    
    @Data
    public static class RegistrationDetail {
    	private String clientId;
        private String clientSecret;
        private String authorizedGrantTypes;
        private String scope;
        private String redirectUri;
        private String clientAuthenticationMethod;
    }
    
    @Data
    public static class Provider {
    	private ProviderDetail oidc;
    }
    
    @Data
    public static class ProviderDetail {
    	private String issuerUri;
    	private String tokenUri;
    }
    
    @Data
    public static class ResourceServer {
        private Jwt jwt;
    }

    @Data
    public static class Jwt {
    	private String issuerUri;
    }

    @Data
    public static class KeyPair {
        private String storePassword;
        private String alias;
    }
}
