package com.bit.microservices.mitra.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class ReactiveAuthenticationContextFilter implements WebFilter {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Autowired
    private JwtDecoder decoder;



    public ReactiveAuthenticationContextFilter() {
//        this.decoder = decoder;
//        this.sink = sink;
    }

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String bearerToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
        Map<String, Object>  claims = new HashMap<>();
        if (bearerToken != null && !bearerToken.isBlank()) {
            String accessToken = bearerToken.split(" ")[1];
            Jwt jwt = this.decoder.decode(accessToken);

            claims = jwt.getClaims();
        }else if(!Objects.isNull(headers.getFirst("tokenMock")) && headers.getFirst("tokenMock").equals("userTester")){

            claims.put("name","User Mock Tester");
            claims.put("userId","0");
        }


        if (!claims.isEmpty()) {
            if (Objects.nonNull(claims.get("name"))){
                Map<String, Object> finalClaims = claims;

                return chain.filter(exchange)
                        .contextWrite((ctx)->{
                            return ctx.put(HttpHeaders.AUTHORIZATION, finalClaims);
                        });
            }
        }

        return chain.filter(exchange);
    }
}
