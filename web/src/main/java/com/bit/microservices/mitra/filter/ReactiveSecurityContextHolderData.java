package com.bit.microservices.mitra.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Slf4j
public class ReactiveSecurityContextHolderData {

    private static final String CLAIM_USER = "name";
    private static final String CLAIM_USER_ID = "userId";



    public static <T> Mono<T> assignContextData(Mono<T> mono){

        return Mono.deferContextual(Mono::just).flatMap((ctx)->{

            Map<String,Object> claim = (Map) ctx.get(HttpHeaders.AUTHORIZATION);
            UserData userData = new UserData();
            userData.setUserName(claim.get(CLAIM_USER).toString());
            userData.setUserId(claim.get(CLAIM_USER_ID).toString());

            UserContextData.setUserData(userData);
            return mono
                    .doFinally((signalType)->{
                     UserContextData.removeUserData();
            });
        });
    }


}
